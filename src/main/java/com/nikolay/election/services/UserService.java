package com.nikolay.election.services;

import com.nikolay.election.entities.Authority;
import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.entities.View;
import com.nikolay.election.repositories.UserRepository;
import com.nikolay.election.repositories.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User findByUsername(String username) {
        return userRepository.findById(username).get();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean existByUsername(String username) {
        return userRepository.existsById(username);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User createNewUser(String username, String password) {
        User user = new User(username, password);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(user));
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean deleteUserByUsername(String username) {
        if (userRepository.existsById(username)) {
            userRepository.deleteById(username);
            return true;
        }
        return false;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<View> getAllViewsByUser(User user) {
        return viewRepository.findAllByUser(user);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public View saveView(View view) {
        return viewRepository.save(view);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<View> getAllViewsByUserAndCandidate(User user, Candidate candidate) {
        return viewRepository.findAllByUserAndCandidate(user, candidate);
    }

    public boolean isCandidateViewedByUser(Candidate candidate, User user) {
        for (View view: user.getViews()) {
            if (view.getCandidate().equals(candidate)) return true;
        }
        return false;
    }
}