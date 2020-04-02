package com.nikolay.election.services;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.entities.View;
import com.nikolay.election.repositories.UserRepository;
import com.nikolay.election.repositories.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private ViewRepository viewRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setViewRepository(ViewRepository viewRepository) {
        this.viewRepository = viewRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findById(username).get();
    }

    ////////////////////

    public List<View> getAllViewsByUser(User user) {
        return viewRepository.findAllByUser(user);
    }

    public View getOneViewByCandidateAndUser(Candidate candidate, User user) {
        return viewRepository.findOneByCandidateAndUser(candidate, user);
    }

    /*public List<Candidate> getAllCandidatesByUser(User user) {
        return userRepository.findAllByUser(user);
    }

    public View saveView(View view) {
        return viewRepository.save(view);
    }*/

    public List<View> saveAllViews(List<View> views) {
        return viewRepository.saveAll(views);
    }
}