package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private VoteRepository voteRepository;

    @Autowired
    public void setVoteRepository (VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<Candidate> getCandidates() {
        return null;
    }

    public List<Candidate> getResult() {
        return null;
    }

    public void voteForCandidate(Long id) {
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