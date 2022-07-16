package com.mykhailotiutiun.myquiz.data.repositories;

import com.mykhailotiutiun.myquiz.data.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);
}
