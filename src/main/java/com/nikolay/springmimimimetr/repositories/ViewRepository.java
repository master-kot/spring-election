package com.nikolay.springmimimimetr.repositories;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {

    public View findOneByCandidateAndUsername(Candidate candidate, String username);

    public List<View> findAllByUsername(String username);
}