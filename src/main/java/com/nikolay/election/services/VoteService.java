package com.nikolay.election.services;

import com.nikolay.election.entities.Vote;
import com.nikolay.election.repositories.VoteRepository;
import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
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

    public Vote findOneByCandidateAndUser(Candidate candidate, User user) {
        return voteRepository.findOneByCandidateAndUser(candidate, user);
    }

    public List<Vote> findAllByCandidate(Candidate candidate) {
        return voteRepository.findAllByCandidate(candidate);
    }

    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    public Integer countByCandidate(Candidate candidate) {
        return voteRepository.countByCandidate(candidate);
    }

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public List<Vote> saveAllVotes(List<Vote> votes) {
        return voteRepository.saveAll(votes);
    }
}