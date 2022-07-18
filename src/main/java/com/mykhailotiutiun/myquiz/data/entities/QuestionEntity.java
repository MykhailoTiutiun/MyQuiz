package com.mykhailotiutiun.myquiz.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String question;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private Map<String, Boolean> answers = new HashMap<>();

    @ManyToOne
    private QuizEntity quiz;

    @ManyToOne
    private ResultEntity result;

    public QuestionEntity() {
    }

    public QuestionEntity(String question, Map<String, Boolean> answers, QuizEntity quiz) {
        this.question = question;
        this.answers = answers;
        this.quiz = quiz;
    }

    public QuestionEntity(String question, Map<String, Boolean> answers, ResultEntity result) {
        this.question = question;
        this.answers = answers;
        this.result = result;
    }
}
