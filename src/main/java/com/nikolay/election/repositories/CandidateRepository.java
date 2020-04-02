package com.nikolay.election.repositories;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    //Vote findOneByCandidateAndUser(Candidate candidate, User user);
    //List<Vote> findAllByCandidate(Candidate candidate);
    //Integer countByCandidate(Candidate candidate);
    //List<User> findAllByCandidate(Candidate candidate);

}