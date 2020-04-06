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

    private List<Candidate> candidates;
    private List<Candidate> notViewedCandidates;
    private List<Vote> votes;
    private List<View> views;

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
        candidates = new ArrayList<>();
        votes  = new ArrayList<>();
        views = new ArrayList<>();
    }

    public List<Candidate> showRandomCandidates() {
        if (notViewedCandidates == null) getNotViewedCandidates();
        if (notViewedCandidates.size() > 0 && candidates.size() == 0) {
            while (candidates.size() < 2) {
                candidates.add(notViewedCandidates
                        .remove(randomise.nextInt(notViewedCandidates.size())));
            }
        } /*else if (notViewedCandidates.size() == 0 && candidates.size() == 0) {
            showElectionResult();
        }*/
        return candidates;
    }

    public List<Candidate> showElectionResult(User user) {
        for (View view : views) {
            view.setUser(user);
        }
        views.removeAll(user.getViews());
        userService.saveAllViews(views);

        candidates = candidateService.getAllCandidates();
        for (Vote vote : votes) {
            vote.setUser(user);
        }
        votes.removeAll(candidateService.getAllVotesByUser(user));
        candidateService.saveAllVotes(votes);

        candidates = candidateService.getAllCandidates();
        candidates.sort(Collections.reverseOrder(Comparator.comparing(obj -> obj.getVotes().size())));
        return candidates;
    }

    public void createVoteForCandidate(Integer id) {
        //защита от возможности голосования на экране результатов
        if (candidates.size() == 2) {
            //пока кандидат не проголосует за эту пару, следующую не увидит
            for (Candidate candidate : candidates) {
                views.add(new View(candidate));
                if (candidate.getId().equals(id)) votes.add(new Vote(candidate));
            }
            candidates.clear();
        }
    }

    private void getNotViewedCandidates() {
        notViewedCandidates = candidateService.getAllCandidates();
    }
}