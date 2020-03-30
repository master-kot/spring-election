package com.nikolay.election.repositories;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.entities.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewRepository extends JpaRepository<View, Integer> {

    View findOneByCandidateAndUser(Candidate candidate, User user);

    List<View> findAllByUser(User user);
}