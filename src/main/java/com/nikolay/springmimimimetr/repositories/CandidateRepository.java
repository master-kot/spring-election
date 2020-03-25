package com.nikolay.springmimimimetr.repositories;

import com.nikolay.springmimimimetr.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}