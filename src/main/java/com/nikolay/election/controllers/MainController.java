package com.nikolay.election.controllers;

import com.nikolay.election.entities.Candidate;
import com.nikolay.election.entities.User;
import com.nikolay.election.services.UserService;
import com.nikolay.election.utils.Election;
import com.nikolay.election.utils.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class MainController {

    private final UserService userService;
    private final Election election;

    @Autowired
    public MainController(UserService userService, Election election) {
        this.userService = userService;
        this.election = election;
    }

    /**
     * Перехват GET-запроса вида: http://localhost:8080/
     */
    @GetMapping("")
    public String getRandomCandidates(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("candidates", election.showCandidates(principal.getName()));
            model.addAttribute("username", principal.getName());
        } else model.addAttribute("candidates", new ArrayList<Candidate>());
        return "show";
    }

    /**
     * Перехват POST-запроса вида: http://localhost:8080/vote/
     *
     * @param request данные, пришедшие от пользователя (id кандидата)
     */
    @PostMapping("/vote")
    public String createVoteForCandidate(@ModelAttribute Request request, Principal principal) {
        if (principal != null) {
            election.createVoteForCandidate(principal.getName(), request.getId());
        }
        return "redirect:/";
    }

    /**
     * Перехват GET-запроса вида: http://localhost:8080/login/
     *
     * @param error сообщение об ошибке
     */
    @GetMapping("/login")
    public String loginUser(@RequestParam(required = false) String error, Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/";
        } else if (error != null) model.addAttribute("error",
                    "пользователь не существует, либо пароль не верный");
        return "login";
    }

    /**
     * Перехват POST-запроса вида: http://localhost:8080/login/
     *
     * @param request данные для регистрации нового пользователя
     */
    @PostMapping("/login")
    public String createUser(@ModelAttribute User request, Model model) {
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            model.addAttribute("user", null);
            model.addAttribute("error", "введенные пароли не совпадают");
        } else if (userService.existByUsername(request.getUsername())) {
            model.addAttribute("user", null);
            model.addAttribute("error",
                    "в системе уже зарегистрирован пользователь с именем " + request.getUsername());
        } else
            model.addAttribute("user",
                    userService.createNewUser(request.getUsername(), "{noop}" + request.getPassword()));
        return "login";
    }

}