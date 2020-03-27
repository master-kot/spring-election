package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.User;
import com.nikolay.springmimimimetr.entities.View;
import com.nikolay.springmimimimetr.entities.Vote;
import com.nikolay.springmimimimetr.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class VoteService {
    private final Random randomise = new Random();
    private VoteRepository voteRepository;
    private CandidateService candidateService;
    private ViewService viewService;

    @Autowired
    public void setVoteRepository (VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Autowired
    public void setViewService (ViewService viewService) {
        this.viewService = viewService;
    }

    @Autowired
    public void setCandidateService (CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    public List<Candidate> getRandomCandidates(User user) {
        int numberOfCandidates = candidateService.getNumberOfCandidates();
        List<Candidate> candidates;
        if (viewService.findAllByUser(user).size() < numberOfCandidates) {
            candidates = new ArrayList<>();
            while (candidates.size() < 2) {
                Candidate randomCandidate = candidateService.getCandidateById(randomise.nextInt(numberOfCandidates) + 1);
                if (viewService.findOneByCandidateAndUser(randomCandidate, user) == null) {
                    viewService.save(new View(user, randomCandidate));
                    candidates.add(randomCandidate);
                }
            }
        } else {
            List<Candidate> allCandidates = candidateService.getAllCandidates();
            for (Candidate candidate : allCandidates) {
                int i = voteRepository.countByCandidate(candidate);
            }

            candidates = candidateService.getAllCandidates();
        }
        return candidates;
    }

    public void voteForCandidate(User user, Integer id) {
        Candidate candidate = candidateService.getCandidateById(id);
        if (voteRepository.findOneByCandidateAndUser(candidate, user) == null
                && id > 0 && id <= candidateService.getNumberOfCandidates()) {
            voteRepository.save(new Vote(user, candidate));
        }
    }
}