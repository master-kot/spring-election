package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.View;
import com.nikolay.springmimimimetr.entities.Vote;
import com.nikolay.springmimimimetr.repositories.ViewRepository;
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
        int CANDIDATE_NUMBER = candidateService.getNumberOfCandidates();
        List<Candidate> randomCandidates = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Long id = ((Integer)(randomise.nextInt(CANDIDATE_NUMBER) + 1)).longValue();
            View view = new View();
            view.setUsername(username);
            view.setCandidate(candidateService.getCandidateById(id));
            viewRepository.save(view);
            randomCandidates.add(candidateService.getCandidateById(id));
        }
        return randomCandidates;
    }

    public List<Candidate> getResult() {
        return candidateService.getAllCandidates();
    }

    public void voteForCandidate(String username, Long id) {
        Vote vote = new Vote();
        vote.setUsername(username);
        vote.setCandidate(candidateService.getCandidateById(id));
        voteRepository.save(vote);
    }



    /*
    private List<Elector> seenElectors;
    private List<Elector> votedElectors;

    {
        this.seenElectors = new ArrayList<Elector>();
        this.votedElectors = new ArrayList<Elector>();
    }

    public void voteForCandidate(Elector elector) {
        if (!isCandidateVoted(elector)) votedElectors.add(elector);
    }

    public void showCandidate(Elector elector) {
        if (!isCandidateSeen(elector)) seenElectors.add(elector);
    }

    public boolean isCandidateSeen(Elector elector) {
        return seenElectors.contains(elector);
    }

    public boolean isCandidateVoted(Elector elector) {
        return votedElectors.contains(elector);
    }

    public int amountOfVotes() {
        return votedElectors.size();
    }
    */
}