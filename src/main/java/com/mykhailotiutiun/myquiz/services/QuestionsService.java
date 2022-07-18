package com.mykhailotiutiun.myquiz.services;

import com.mykhailotiutiun.myquiz.data.entities.QuestionEntity;
import com.mykhailotiutiun.myquiz.data.entities.QuizEntity;
import com.mykhailotiutiun.myquiz.data.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

@Service
public class QuestionsService {

    @Autowired
    private QuestionsRepository questionsRepository;

    public List<QuestionEntity> getAllQuestionsByQuiz(QuizEntity quizEntity){
        return questionsRepository.findAllByQuiz(quizEntity);
    }

    public QuestionEntity getQuestionById(Long id){
        return questionsRepository.findById(id).get();
    }

    public void createQuestion(String question, QuizEntity quizEntity){
        questionsRepository.save(new QuestionEntity(question, new HashMap<>(), quizEntity));
    }

    @Transactional
    public void addAnswer(Long questionId, String answer, Boolean isTrue){
        if (isTrue == null) {
            isTrue = false;
        }
        QuestionEntity questionEntity = getQuestionById(questionId);
        questionEntity.getAnswers().put(answer, isTrue);
    }

    @Transactional
    public void removeAnswer(Long questionId, String answer){
        QuestionEntity questionEntity = getQuestionById(questionId);
        questionEntity.getAnswers().remove(answer);
    }

    public void deleteQuestion(Long questionId){
        questionsRepository.deleteById(questionId);
    }
}
