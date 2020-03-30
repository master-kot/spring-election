package com.nikolay.election.services;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private CandidateRepository candidateRepository;

    @Autowired
    public void setCandidateRepository(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Integer getNumberOfCandidates() {
        return (int) candidateRepository.count();
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Integer id) {
        return candidateRepository.getOne(id);
    }
}