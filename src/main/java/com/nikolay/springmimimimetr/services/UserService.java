package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.User;
import com.nikolay.springmimimimetr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findById(username).get();
    }
}