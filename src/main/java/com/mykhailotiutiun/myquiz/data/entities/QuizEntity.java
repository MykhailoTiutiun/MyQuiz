package com.mykhailotiutiun.myquiz.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "quiz")
public class QuizEntity {

    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    @ManyToOne
    private UserEntity creator;
    @OneToMany(mappedBy = "quiz")
    private Set<QuestionAnswerEntity> questions = new HashSet<>();

    @OneToMany(mappedBy = "quiz")
    private Set<ResultEntity> results = new HashSet<>();

    public QuizEntity() {
    }

    public QuizEntity(String name, UserEntity creator) {
        this.name = name;
        this.creator = creator;
    }
}
