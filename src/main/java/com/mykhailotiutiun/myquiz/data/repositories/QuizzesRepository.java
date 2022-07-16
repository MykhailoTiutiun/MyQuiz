package com.mykhailotiutiun.myquiz.data.repositories;

import com.mykhailotiutiun.myquiz.data.entities.QuizEntity;
import com.mykhailotiutiun.myquiz.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizzesRepository extends JpaRepository<QuizEntity, Long> {

    List<QuizzesRepository> findAllByCreator(UserEntity creator);
}
