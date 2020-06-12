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

    private final UserRepository userRepository;
    private final ViewRepository viewRepository;

    @Autowired
    public UserService(UserRepository userRepository, ViewRepository viewRepository) {
        this.userRepository = userRepository;
        this.viewRepository = viewRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findById(username).get();
    }

    public boolean existByUsername(String username) {
        return userRepository.existsById(username);
    }

    public User createNewUser(String username, String password) {
        User user = new User(username, password);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(user));
        user.setAuthorities(authorities);
        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public View saveView(View view) {
        return viewRepository.save(view);
    }

    public boolean isCandidateViewedByUser(Candidate candidate, User user) {
        for (View view: user.getViews()) {
            if (view.getCandidate().equals(candidate)) return true;
        }
        return false;
    }
}