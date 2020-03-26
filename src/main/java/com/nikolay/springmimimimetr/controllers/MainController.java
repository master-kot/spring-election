package com.nikolay.springmimimimetr.controllers;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class MainController {

    private VoteService voteService;

    @Autowired
    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/
    @GetMapping("/")
    public String getRandomCandidates(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("candidates", voteService.getRandomCandidates(principal.getName()));
        } else model.addAttribute("candidates", new ArrayList<Candidate>());
        return "show";
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/vote/1
    @GetMapping("/vote/{id}")
    public String voteForCandidate(@PathVariable("id") Long id, Principal principal) {
        if (principal != null) voteService.voteForCandidate(principal.getName(), id);
        return "redirect:/";
    }
}