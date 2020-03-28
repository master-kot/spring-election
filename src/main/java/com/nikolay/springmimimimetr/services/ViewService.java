package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.User;
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

    public List<View> findAllByUser(User user) {
        return viewRepository.findAllByUser(user);
    }

    public View findOneByCandidateAndUser(Candidate candidate, User user) {
        return viewRepository.findOneByCandidateAndUser(candidate, user);
    }

    public View saveView(View view) {
        return viewRepository.save(view);
    }

    public List<View> saveAllViews(List<View> views) {
        return viewRepository.saveAll(views);
    }
}