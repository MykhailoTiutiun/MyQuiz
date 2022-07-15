package com.mykhailotiutiun.myquiz.data.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class RoleEntity implements GrantedAuthority {

    @Id
    private Long id;
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    public RoleEntity() {
    }

    public RoleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
