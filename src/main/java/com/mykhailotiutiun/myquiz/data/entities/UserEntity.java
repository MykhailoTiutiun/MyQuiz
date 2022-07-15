package com.mykhailotiutiun.myquiz.data.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany
    private Set<RoleEntity> roles = new HashSet<>();
    private LocalDate initDate;

    @OneToMany(mappedBy = "creator")
    private Set<QuizEntity> createdQuizzes = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Set<ResultEntity> results = new HashSet<>();

    public UserEntity(){}

    public UserEntity(String username, String email, String password, String passwordConfirm) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.initDate = LocalDate.now();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

}
