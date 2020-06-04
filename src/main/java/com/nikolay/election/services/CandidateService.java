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
    public CandidateService(CandidateRepository candidateRepository, VoteRepository voteRepository) {
        this.candidateRepository = candidateRepository;
        this.voteRepository = voteRepository;
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Integer id) {
        return candidateRepository.getOne(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public boolean isCandidateVotedByUser(Candidate candidate, User user) {
        for (Vote vote : candidate.getVotes()) {
            if (vote.getUser().equals(user)) return true;
        }
        return false;
    }
}