package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.View;
import com.nikolay.springmimimimetr.repositories.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewService {

    private ViewRepository viewRepository;

    @Autowired
    public void setViewRepository(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    public List<View> findAllByUsername(String username) {
        return viewRepository.findAllByUsername(username);
    }

    public View findOneByCandidateAndUsername(Candidate candidate, String username) {
        return viewRepository.findOneByCandidateAndUsername(candidate, username);
    }

    public View save(View view) {
        return viewRepository.save(view);
    }
}