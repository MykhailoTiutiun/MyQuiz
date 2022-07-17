package com.mykhailotiutiun.myquiz.web.controllers;

import com.mykhailotiutiun.myquiz.data.entities.QuizEntity;
import com.mykhailotiutiun.myquiz.services.QuizzesService;
import com.mykhailotiutiun.myquiz.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizzesController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private QuizzesService quizzesService;

    private Long chosenQuizId = null;

    @GetMapping("/quizzes-manager")
    public String quizzesPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("quizzes", quizzesService.getAllQuizzesByUser(usersService.getUserByName(auth.getName())));
        model.addAttribute("quizForm", new QuizEntity());
        return "quizzes-manager/main";
    }

    @PostMapping("/quizzes-manager/create-quiz")
    public String createQuiz(@ModelAttribute QuizEntity quizEntity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        quizzesService.createQuiz(quizEntity, usersService.getUserByName(auth.getName()));
        return "redirect:/quizzes-manager";
    }

    @PostMapping("/quizzes-manager")
    public String quizActions(@RequestParam(name = "action") String action, @RequestParam(name = "quizId") Long quizId, Model model) {
        switch (action) {
            case ("change-questions") -> {chosenQuizId = quizId; return "redirect:/quizzes-manager/questions";}
            case ("delete") -> quizzesService.deleteQuiz(quizId);
            default -> throw new IllegalStateException("Unexpected value: " + action);
        }
        return "redirect:/quizzes-manager";
    }


}
