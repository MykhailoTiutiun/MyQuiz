package com.mykhailotiutiun.myquiz.web.controllers;

import com.mykhailotiutiun.myquiz.data.entities.QuestionEntity;
import com.mykhailotiutiun.myquiz.data.entities.QuizEntity;
import com.mykhailotiutiun.myquiz.services.QuestionsService;
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
    @Autowired
    private QuestionsService questionsService;

    private Long chosenQuizId = null;

    private Long chosenQuestionId = -1L;

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

    @GetMapping("/quizzes-manager/questions")
    public String questionsPage(Model model){
        if (chosenQuizId == null) {
            return "redirect:/quizzes-manager";
        }

        QuestionEntity chosenQuestion;
        if(chosenQuestionId < 0) {
            chosenQuestion = new QuestionEntity();
        } else {
            chosenQuestion = questionsService.getQuestionById(chosenQuestionId);
        }

        model.addAttribute("quiz", quizzesService.getQuizById(chosenQuizId));
        model.addAttribute("questions", questionsService.getAllQuestionsByQuiz(quizzesService.getQuizById(chosenQuizId)));
        model.addAttribute("selectedQuestion", chosenQuestion);
        return "quizzes-manager/questions";

    }

    @PostMapping("/quizzes-manager/questions")
    public String questionsActions(@RequestParam(name = "action") String action, @RequestParam(name = "questionId", required = false) Long questionId, @RequestParam(name = "name", required = false) String name, @RequestParam(name = "isTrue", required = false) Boolean isTrue, Model model) {
        switch (action) {
            case ("create") -> questionsService.createQuestion(name, quizzesService.getQuizById(chosenQuizId));
            case ("selectQuestion") -> {chosenQuestionId = questionId; return "redirect:/quizzes-manager/questions";}
            case ("createAnswer") -> questionsService.addAnswer(questionId, name, isTrue);
            case ("deleteAnswer") -> questionsService.removeAnswer(questionId, name);
            case ("delete") -> questionsService.deleteQuestion(questionId);
            default -> throw new IllegalStateException("Unexpected value: " + action);
        }
        return "redirect:/quizzes-manager/questions";
    }
}
