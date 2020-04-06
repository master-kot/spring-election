package com.nikolay.election.controllers;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.services.UserService;
import com.nikolay.election.utils.Election;
import com.nikolay.election.utils.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class MainController {
    private UserService userService;
    private Election election;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setElection(Election election) {
        this.election = election;
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/
    @GetMapping("/")
    public String getRandomCandidates(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("candidates", election.showElectionResult(user));
        } else model.addAttribute("candidates", election.showRandomCandidates());
        return "show";
    }

    //Перехват POST-запроса вида: http://localhost:8080/election/vote/
    @PostMapping("/vote")
    public String createVoteForCandidate(@ModelAttribute UserRequest request) {
        election.createVoteForCandidate(request.getId());
        return "redirect:/";
    }

    //Перехват POST-запроса вида: http://localhost:8080/election/
    @PostMapping("/")
    public String  createProfile(@ModelAttribute @Valid UserRequest request, Model model) {
        if (!request.getPassword().equals(request.getPasswordConfirm()))
            model.addAttribute("error", "Ошибка: введенные пароли не совпадают");
        else if (userService.existByUsername(request.getUsername()))
            model.addAttribute("error", "Ошибка: в системе уже зарегистрирован пользователь с именем " + request.getUsername());
        else model.addAttribute("user", userService.createNewUser(request.getUsername(), request.getPassword()));
        return "registration";
    }
}