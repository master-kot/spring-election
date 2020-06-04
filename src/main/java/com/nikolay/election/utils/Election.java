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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Создается отдельно для каждой сессии, содержит защиту для продолжения голосования
 * в случае если пользователь проголосовал и просмотрел не всех кандидатов
 */
@Transactional
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Election {

    private final Random randomise = new Random();

    private List<Candidate> candidates;
    private List<Candidate> notViewedCandidates;

    private final CandidateService candidateService;
    private final UserService userService;

    @Autowired
    public Election(CandidateService candidateService, UserService userService) {
        this.candidateService = candidateService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        candidates = new ArrayList<>();
        notViewedCandidates = candidateService.getAllCandidates();
    }

    public List<Candidate> showCandidates(String username) {
        getNotViewedCandidates(username);
        if (notViewedCandidates.size() == 0) {
            //выводим список кандидатов, топ 10 обрабатывается на странице с помощью Thymeleaf
            candidates = candidateService.getAllCandidates();
            candidates.sort(Collections
                    .reverseOrder(Comparator.comparing(obj -> obj.getVotes().size())));
        } else if (candidates.size() == 0) {
            for (int i = 0; i < 2; i++) {
                candidates.add(notViewedCandidates
                        .remove(randomise.nextInt(notViewedCandidates.size())));
            }
        }
        return candidates;
    }

    /**
     * пока избиратель не проголосует за одного из кандидатов этой пары,
     * просмотры этой пары кандидатов пользователем не будут сохранены
     */
    public void createVoteForCandidate(String username, Integer id) {
        User user = userService.findByUsername(username);
        for (Candidate candidate : candidates) {
            if (!userService.isCandidateViewedByUser(candidate, user)) {
                userService.saveView(new View(user, candidate));
                if (candidate.getId().equals(id))
                    candidateService.saveVote(new Vote(user, candidate));
            }
        }
        candidates.clear();
    }

    private void getNotViewedCandidates(String username) {
        User user = userService.findByUsername(username);
        for (View view : user.getViews()) {
            notViewedCandidates.remove(view.getCandidate());
        }
    }

}