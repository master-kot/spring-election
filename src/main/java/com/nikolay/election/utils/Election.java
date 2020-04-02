package com.nikolay.election.utils;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.entities.View;
import com.nikolay.election.entities.Vote;
import com.nikolay.election.services.CandidateService;
import com.nikolay.election.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Election {

    private final Random randomise = new Random();

    private List<Candidate> notVotedCandidates;
    private List<Candidate> notViewedCandidates;
    private List<Vote> newVotes;
    private List<View> newViews;

    private CandidateService candidateService;
    private UserService userService;

    @Autowired
    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        newViews = new ArrayList<>();
        newVotes = new ArrayList<>();
    }

    public List<Candidate> showCandidates(User user) {
        List<Candidate> candidates = new ArrayList<>();
        if (notViewedCandidates == null) getNotViewedCandidates(user);
        if (notViewedCandidates.size() > 0) {
            while (candidates.size() < 2) {
                Candidate randomCandidate = notViewedCandidates.remove(randomise.nextInt(notViewedCandidates.size()));
                newViews.add(new View(user, randomCandidate));
                candidates.add(randomCandidate);
            }
        } else {
            userService.saveAllViews(newViews);
            candidates = candidateService.getAllCandidates();
            candidates.sort(Collections.reverseOrder(Comparator.comparing(obj -> obj.getVotes().size())));
        }
        return candidates;
    }
    
    private void getNotViewedCandidates(User user) {
        notViewedCandidates = candidateService.getAllCandidates();
        for (View view : userService.getAllViewsByUser(user)) {
            notViewedCandidates.remove(view.getCandidate());
        }
    }

    private void getNotVotedCandidates(User user) {
        notVotedCandidates = candidateService.getAllCandidates();
        for (Vote vote : candidateService.getAllVotesByUser(user)) {
            notVotedCandidates.remove(vote.getCandidate());
        }
    }

    public void voteForCandidate(User user, Integer id) {
        if (id > 0 && id <= candidateService.countCandidates()) {
            Candidate candidate = candidateService.getCandidateById(id);
            if (candidateService.getOneVoteByCandidateAndUser(candidate, user) == null
                &&userService.getOneViewByCandidateAndUser(candidate, user) == null){
                candidateService.saveVote(new Vote(user, candidate));
            }
        }
    }
}