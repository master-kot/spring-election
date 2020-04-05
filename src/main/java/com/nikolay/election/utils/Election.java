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

import java.util.*;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Election {

    private final Random randomise = new Random();

    private List<Candidate> candidates;
    private List<Candidate> notViewedCandidates;

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

    public List<Candidate> showCandidates(User user) {
        candidates = new ArrayList<>();
        if (notViewedCandidates == null) getNotViewedCandidates(user);
        if (notViewedCandidates.size() > 0) {
            int lastNumber = -1;
            while (candidates.size() < 2) {
                int randomNumber = randomise.nextInt(notViewedCandidates.size());
                if (lastNumber != randomNumber) {
                    candidates.add(notViewedCandidates.get(lastNumber = randomNumber));
                }
            }
        } else {
            candidates = candidateService.getAllCandidates();
            candidates.sort(Collections.reverseOrder(Comparator.comparing(obj -> obj.getVotes().size())));
        }
        return candidates;
    }

    public void createVoteForCandidate(User user, Integer id) {
        for (Candidate candidate : candidates) {
            if (notViewedCandidates.remove(candidate)) {
                userService.saveView(new View(user, candidate));
                if (candidate.getId().equals(id)) candidateService.saveVote(new Vote(user, candidate));
            }
        }
    }

    private void getNotViewedCandidates(User user) {
        notViewedCandidates = candidateService.getAllCandidates();
        for (View view : userService.getAllViewsByUser(user)) {
            notViewedCandidates.remove(view.getCandidate());
        }
    }
}