package com.nikolay.springmimimimetr.services;

import com.nikolay.springmimimimetr.entities.Candidate;
import com.nikolay.springmimimimetr.entities.User;
import com.nikolay.springmimimimetr.entities.View;
import com.nikolay.springmimimimetr.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ElectionService {

    private final Random randomise = new Random();
    private VoteService voteService;
    private CandidateService candidateService;
    private ViewService viewService;

    @Autowired
    public void setVoteService (VoteService voteService) {
        this.voteService = voteService;
    }

    @Autowired
    public void setViewService (ViewService viewService) {
        this.viewService = viewService;
    }

    @Autowired
    public void setCandidateService (CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    /**
     * Применена оптимизация количества обращений к БД во время поиска
     * случайного кандидата, просмотры кандидатов сохраняются тогда,
     * когда найдены и отправлены в список для просмотра оба случайных кандидата
     */
    public List<Candidate> showCandidates(User user) {
        List<Candidate> candidates = new ArrayList<>();
        List<Candidate> allCandidates = candidateService.getAllCandidates();
        int numberOfCandidates = allCandidates.size();

        List<View> previousViews = viewService.findAllByUser(user);
        if (previousViews.size() < numberOfCandidates) {
            List<View> nextViews = new ArrayList<>();
            while (candidates.size() < 2) {
                Candidate randomCandidate = allCandidates.get(randomise.nextInt(numberOfCandidates));
                /* Защита от повторного просмотра кандидатов.
                 * Применено сравнение по списку просмотров вместо повторения запросов просмотров,
                 * т.к. просмотров может быть не более количества кандидатов,
                 * то это работает быстрее, чем каждую итерацию делать новый запрос через сервис.
                */
                if (findViewByCandidate(randomCandidate, previousViews) == null
                        && findViewByCandidate(randomCandidate, nextViews) == null) {
                    nextViews.add(new View(user, randomCandidate));
                    candidates.add(randomCandidate);
                }
            }
            viewService.saveAllViews(nextViews);
        } else {
            /* Поскольку список приходит с БД в неотсортированном виде,
             * сортируем его в обратном порядке пузырьком либо методом sort.
             */
            allCandidates.sort(Collections.reverseOrder(Comparator.comparing(obj -> obj.getVotes().size())));
            /*for (int i = 0; i < allCandidates.size(); i++) {
                for (int j = 0; j < allCandidates.size() - i - 1; j++) {
                    if(allCandidates.get(j).getVotes().size() < allCandidates.get(j+1).getVotes().size()) {
                        Candidate tmp = allCandidates.get(j);
                        allCandidates.set(j, allCandidates.get(j+1));
                        allCandidates.set(j+1, tmp);
                    }
                }
            }*/
            /* Первые 10 кандидатов из списка любого размера также могут быть извлечены непосредственно
             * при обработке страницы с помощью Thymeleaf методом th:if="${candidates.indexOf(candidate) < 10}"
             * тогда вместо следующих трех строк присваиваем значение: candidates = allCandidates.
             */
            for (int i = 0; i < 10; i++) {
                candidates.add(allCandidates.get(i));
            }
        }
        return candidates;
    }

    private View findViewByCandidate (Candidate candidate, List<View> views) {
        for (View view : views) {
            if(view.getCandidate().equals(candidate)) return view;
        }
        return null;
    }

    public void voteForCandidate(User user, Integer id) {
        Candidate candidate = candidateService.getCandidateById(id);
        /*защита от повторного голосования и использования
         *неправильных ссылок на несуществующего кандидата
         */
        if (voteService.findOneByCandidateAndUser(candidate, user) == null
                && id > 0 && id <= candidateService.getNumberOfCandidates()) {
            voteService.saveVote(new Vote(user, candidate));
        }
    }
}