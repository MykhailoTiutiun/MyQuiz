package com.mykhailotiutiun.myquiz.data.repositories;

import com.mykhailotiutiun.myquiz.data.entities.ResultEntity;
import com.mykhailotiutiun.myquiz.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Long> {

    List<ResultEntity> findAllByUser(UserEntity user);
}
