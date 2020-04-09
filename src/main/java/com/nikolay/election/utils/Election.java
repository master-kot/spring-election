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
    }

    public List<Candidate> showCandidates(User user) {
        if (notViewedCandidates == null) getNotViewedCandidates(user);
        if (notViewedCandidates.size() > 0 && candidates.size() == 0) {
            while (candidates.size() < 2) {
                candidates.add(notViewedCandidates
                        .remove(randomise.nextInt(notViewedCandidates.size())));
            }
        } else if (notViewedCandidates.size() == 0 && candidates.size() == 0) {
            //выводим список кандидатов, топ 10 обрабатывается на странице с помощью Thymeleaf
            candidates = candidateService.getAllCandidates();
            candidates.sort(Collections.reverseOrder(Comparator.comparing(obj -> obj.getVotes().size())));
        }
        return candidates;
    }

    public void createVoteForCandidate(User user, Integer id) {
        //защита от возможности голосования на экране результатов
        if (candidates.size() == 2) {
            //пока кандидат не проголосует за эту пару, следующую не увидит
            for (Candidate candidate : candidates) {
                userService.saveView(new View(user, candidate));
                if (candidate.getId().equals(id)) candidateService.saveVote(new Vote(user, candidate));
            }
            candidates.clear();
        }
    }

    private void getNotViewedCandidates(User user) {
        notViewedCandidates = candidateService.getAllCandidates();
        for (View view : userService.getAllViewsByUser(user)) {
            notViewedCandidates.remove(view.getCandidate());
        }
    }
}