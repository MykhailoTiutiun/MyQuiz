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
    private Long id;
    private String name;
    @ManyToOne
    private UserEntity creator;
    @OneToMany(mappedBy = "quiz")
    private Set<QuestionAnswerEntity> questions = new HashSet<>();

    @OneToMany(mappedBy = "quiz")
    private Set<ResultEntity> results = new HashSet<>();

    public QuizEntity() {
    }

    public QuizEntity(Long id, String name, UserEntity creator) {
        this.id = id;
        this.name = name;
        this.creator = creator;
    }
}
