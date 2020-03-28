package com.nikolay.springmimimimetr.repositories;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.User;
import com.nikolay.springmimimimetr.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, Integer> {

    Vote findOneByCandidateAndUser(Candidate candidate, User user);

    List<Vote> findAllByCandidate(Candidate candidate);

    Integer countByCandidate(Candidate candidate);
}