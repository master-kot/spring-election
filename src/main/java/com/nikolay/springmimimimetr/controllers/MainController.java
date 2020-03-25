package com.nikolay.springmimimimetr.controllers;

import com.nikolay.springmimimimetr.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

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
        model.addAttribute("candidates", voteService.getRandomCandidates(principal.getName()));
        return "show";
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/vote/1
    @GetMapping("/vote/{id}")
    public String voteForCandidate(@PathVariable("id") Long id, Principal principal) {
        voteService.voteForCandidate(principal.getName(), id);
        return "redirect:/";
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/results
    @GetMapping("/results")
    public String getVoteResult(Model model) {
        model.addAttribute("candidates", voteService.getResult());
        return "results";
    }
}