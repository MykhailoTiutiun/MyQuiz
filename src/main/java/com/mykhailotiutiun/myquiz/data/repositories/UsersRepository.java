package com.mykhailotiutiun.myquiz.data.repositories;

import com.mykhailotiutiun.myquiz.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);

}
