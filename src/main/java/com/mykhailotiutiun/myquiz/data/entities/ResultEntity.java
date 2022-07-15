package com.mykhailotiutiun.myquiz.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "result")
public class ResultEntity {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private QuizEntity quiz;

    @OneToMany(mappedBy = "result")
    private Set<QuestionAnswerEntity> questionsAnswers = new HashSet<>();
    @ManyToOne
    private UserEntity user;

    public ResultEntity() {}

    public ResultEntity(QuizEntity quiz, Set<QuestionAnswerEntity> questionsAnswers, UserEntity user) {
        this.quiz = quiz;
        this.questionsAnswers = questionsAnswers;
        this.user = user;
    }
}
