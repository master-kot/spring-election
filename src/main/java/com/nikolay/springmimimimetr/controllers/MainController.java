package com.nikolay.springmimimimetr.controllers;

import com.nikolay.springmimimimetr.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    private VoteService voteService;

    @Autowired
    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/index
    @GetMapping("/index")
    public String homePage() {
        return "index";
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/show
    @GetMapping("/show")
    public String showCandidates(Model model) {
        model.addAttribute("candidates", voteService.getCandidates());
        return "show";
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/vote/1
    @GetMapping("/vote/{id}")
    public String voteForCandidate(@PathVariable("id") Long id) {
        voteService.voteForCandidate(id);
        return "redirect:/show";
    }

    //Перехват GET-запроса вида: http://localhost:8080/election/result
    @GetMapping("/result")
    public String allNumbersPage(Model model) {
        model.addAttribute("candidates", voteService.getResult());
        return "result";
    }
}