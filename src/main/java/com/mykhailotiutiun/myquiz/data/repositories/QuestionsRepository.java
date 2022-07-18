package com.mykhailotiutiun.myquiz.data.repositories;

import com.mykhailotiutiun.myquiz.data.entities.QuestionEntity;
import com.mykhailotiutiun.myquiz.data.entities.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<QuestionEntity, Long> {

    List<QuestionEntity> findAllByQuestion(String Question);
    List<QuestionEntity> findAllByQuiz(QuizEntity quizEntity);
}