package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private CandidateRepository candidateRepository;

    @Autowired
    public void setProductRepository(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).get();
    }

    public void deleteCandidateById(Long id) {
        candidateRepository.deleteById(id);
    }
}