package com.nikolay.springmimimimetr.repositories;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, Integer> {

    public Vote findOneByCandidateAndUsername(Candidate candidate, String username);

    public List<Vote> findAllByCandidate(Candidate candidate);

    public int countByCandidate(Candidate candidate);
}