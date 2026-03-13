package com.project.expense.controller;

import com.project.expense.service.AuthService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Builder
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/")
    public String defaultLoginPage(){
        return "login-form";
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login-form";
    }

    @GetMapping("/register")
    public String showRegisterPage(){
        return "register-form";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String userName,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String fullName){
    authService.register(userName,password,email,fullName);
    return "redirect:/login";
    }
}
