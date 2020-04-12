package com.nikolay.election.services;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.entities.Vote;
import com.nikolay.election.repositories.CandidateRepository;
import com.nikolay.election.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CandidateService {

    private CandidateRepository candidateRepository;
    private VoteRepository voteRepository;

    @Autowired
    public void setCandidateRepository(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Autowired
    public void setVoteRepository (VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Candidate getCandidateById(Integer id) {
        return candidateRepository.getOne(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }
}