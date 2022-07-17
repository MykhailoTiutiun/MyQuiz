package com.mykhailotiutiun.myquiz.services;

import com.mykhailotiutiun.myquiz.data.entities.QuizEntity;
import com.mykhailotiutiun.myquiz.data.entities.RoleEntity;
import com.mykhailotiutiun.myquiz.data.entities.UserEntity;
import com.mykhailotiutiun.myquiz.data.repositories.RolesRepository;
import com.mykhailotiutiun.myquiz.data.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class UsersService implements UserDetailsService {

    @Value(value = "${user.temporal-admin-password}")
    private String temporalAdminPassword;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RolesRepository rolesRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = usersRepository.findByEmail(email);

        return user;
    }

    public UserEntity getUserByName(String name){return usersRepository.findByUsername(name);}
    public UserEntity getUserByEmail(String email){return usersRepository.findByEmail(email);}
    public UserEntity getUserById(Long userId) {
        Optional<UserEntity> userFromDb = usersRepository.findById(userId);
        return userFromDb.orElse(new UserEntity());
    }
    public List<UserEntity> getAllUsers() {
        return usersRepository.findAll();
    }
    public boolean verifyUser(String name, String password, RoleEntity requestRole){
        return bCryptPasswordEncoder.matches(password, getUserByName(name).getPassword()) && getUserByName(name).getRoles().contains(requestRole);
    }

    public boolean isUserHaveRole(UserEntity userEntity, Long roleId){
        return userEntity.getRoles().contains(rolesRepository.findById(roleId).get());
    }

    public void createUser(UserEntity userEntity){
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

        userEntity.setInitDate(LocalDate.now());

        addRole(userEntity, 1L);

        if(bCryptPasswordEncoder.matches(temporalAdminPassword, userEntity.getPassword())){
            addRole(userEntity, 0L);
            addRole(userEntity, 2L);
            addRole(userEntity, 3L);
        }

        usersRepository.save(userEntity);
    }

    @Transactional
    public void addQuiz(UserEntity userEntity, QuizEntity quizEntity){
        Set<QuizEntity> quizEntities = userEntity.getCreatedQuizzes();
        quizEntities.add(quizEntity);
        userEntity.setCreatedQuizzes(quizEntities);
    }

    public void addRole(UserEntity userEntity, Long roleId){
        Set<RoleEntity> roles = userEntity.getRoles();
        roles.add(rolesRepository.findById(roleId).get());
        userEntity.setRoles(roles);
    }

    public void removeRole(UserEntity userEntity, Long roleId){
        Set<RoleEntity> roles = userEntity.getRoles();
        roles.remove(rolesRepository.findById(roleId).get());
        userEntity.setRoles(roles);
    }

    public void deleteUser(Long id){
        usersRepository.deleteById(id);
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    private void deleteTemporalUser(){
        List<UserEntity> userEntities = getAllUsers().stream().filter(userEntity -> isUserHaveRole(userEntity, 0L)).toList();
        usersRepository.deleteAll(userEntities);
    }

}
