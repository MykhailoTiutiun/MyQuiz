package com.mykhailotiutiun.myquiz.web.controllers;

import com.mykhailotiutiun.myquiz.data.entities.UserEntity;
import com.mykhailotiutiun.myquiz.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class MainController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/sing-in").setViewName("main/sing-in");
        registry.addViewController("/").setViewName("main/index");
    }

    @Autowired
    UsersService usersService;

    String error = null;

    @GetMapping("/sing-up")
    public String registration(Model model){
        model.addAttribute("userForm", new UserEntity());
        model.addAttribute("error", error);
        return "main/sing-up";
    }

    @PostMapping("/sing-up")
    public String addUser(@ModelAttribute UserEntity userEntity, BindingResult result, Model model){

        if (usersService.getUserByName(userEntity.getUsername()) != null) {
            error = "User with this username already exists!";
        } else if (usersService.getUserByEmail(userEntity.getEmail()) != null) {
            error = "User with this email already exists!";
        } else if (!userEntity.getPassword().equals(userEntity.getPasswordConfirm())){
            error = "Passwords are different!";
        } else if (!result.hasErrors()){
            usersService.createUser(userEntity);
            return "redirect:/";
        }
        return "redirect:/sing-up";
    }
}
