package com.mykhailotiutiun.myquiz.services;

import com.mykhailotiutiun.myquiz.data.entities.QuizEntity;
import com.mykhailotiutiun.myquiz.data.entities.UserEntity;
import com.mykhailotiutiun.myquiz.data.repositories.QuizzesRepository;
import com.mykhailotiutiun.myquiz.data.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuizzesService {
    @Autowired
    private QuizzesRepository quizzesRepository;

    @Autowired
    private UsersService usersService;

    public List<QuizEntity> getAllQuizzes(){
        return quizzesRepository.findAll();
    }

    public List<QuizEntity> getAllQuizzesByUser(UserEntity userEntity){
        return quizzesRepository.findAllByCreator(userEntity);
    }

    public void createQuiz(QuizEntity quizEntity, UserEntity userEntity){
        quizEntity.setId(generateId(100000L, 1000000L));
        quizEntity.setCreator(userEntity);
        quizzesRepository.save(quizEntity);
    }

    private Long generateId(Long min, Long max){
        Long quizId;
        do {
            quizId = Math.round((Math.random() * (max - min)) + min);;
        } while (!quizzesRepository.findById(quizId).isEmpty());
        return quizId;
    }

    public void deleteQuiz(Long quizId){
        quizzesRepository.deleteById(quizId);
    }

}
