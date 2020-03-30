package com.nikolay.election.controllers;

import com.nikolay.election.services.ElectionService;
import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class MainController {
    private ElectionService electionService;
    private UserService userService;

    @Autowired
    public void setVoteService(ElectionService electionService) {
        this.electionService = electionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/
    @GetMapping("/")
    public String getRandomCandidates(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("candidates", electionService.showCandidates(user));
        } else model.addAttribute("candidates", new ArrayList<Candidate>());
        return "show";
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/vote/1
    @GetMapping("/vote/{id}")
    public String voteForCandidate(@PathVariable("id") Integer id, Principal principal) {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            electionService.voteForCandidate(user, id);
        }
        return "redirect:/";
    }
}