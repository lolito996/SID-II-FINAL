package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.postgres.User;

import org.springframework.ui.Model;


@Controller
public class WebAuthController {
    @GetMapping("/login")
    public String login(User user, Model model) {
        return "auth/login"; 
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register"; 
    }
}