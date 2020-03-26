package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.View;
import com.nikolay.springmimimimetr.entities.Vote;
import com.nikolay.springmimimimetr.repositories.ViewRepository;
import com.nikolay.springmimimimetr.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class VoteService {
    private final Random randomise = new Random();
    private VoteRepository voteRepository;
    private CandidateService candidateService;
    private ViewRepository viewRepository;

    @Autowired
    public void setVoteRepository (VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Autowired
    public void setViewRepository (ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    @Autowired
    public void setCandidateService (CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    public List<Candidate> getRandomCandidates(String username) {
        int numberOfCandidates = candidateService.getNumberOfCandidates();
        List<Candidate> candidates;
        if (viewRepository.findAllByUsername(username).size() < numberOfCandidates) {
            candidates = new ArrayList<>();
            while (candidates.size() < 2) {
                Long id = ((Integer) (randomise.nextInt(numberOfCandidates) + 1)).longValue();
                Candidate randomCandidate = candidateService.getCandidateById(id);
                //if (viewRepository.findOneByCandidateAndUsername(id, username) == null) {
                if (viewRepository.findOneByCandidateAndUsername(randomCandidate, username) == null) {
                    View view = new View();
                    view.setUsername(username);
                    //view.setCandidate(id);
                    view.setCandidate(randomCandidate);
                    viewRepository.save(view);
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

    public void voteForCandidate(String username, Long id) {
        Candidate candidateById = candidateService.getCandidateById(id);
        //if (voteRepository.findOneByCandidateAndUsername(id, username) == null) {
        if (voteRepository.findOneByCandidateAndUsername(candidateById, username) == null) {
            Vote vote = new Vote();
            vote.setUsername(username);
            //vote.setCandidate(id);
            vote.setCandidate(candidateById);
            voteRepository.save(vote);
        }
    }
}