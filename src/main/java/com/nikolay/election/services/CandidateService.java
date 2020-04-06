package com.nikolay.election.services;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.entities.Vote;
import com.nikolay.election.repositories.CandidateRepository;
import com.nikolay.election.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Integer id) {
        return candidateRepository.getOne(id);
    }

    public List<Vote> getAllVotesByUser(User user) {
        return voteRepository.findAllByUser(user);
    }

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public List<Vote> saveAllVotes(List<Vote> votes) {
        return voteRepository.saveAll(votes);
    }
}