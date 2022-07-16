package com.mykhailotiutiun.myquiz.data.repositories;

import com.mykhailotiutiun.myquiz.data.entities.QuestionAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsAnswersRepository extends JpaRepository<QuestionAnswerEntity, Long> {

    List<QuestionAnswerEntity> findAllByQuestion(String Question);
}