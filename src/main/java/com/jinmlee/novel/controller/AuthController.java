package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.JoinDto;
import com.jinmlee.novel.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login(){

        return "auth/login";
    }

    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("joinDto", new JoinDto());
        return "auth/join";
    }
    @PostMapping("/join")
    public String joinProcess(@ModelAttribute JoinDto joinDto, Model model){

        authService.join(joinDto);
        return "redirect:/auth/login";
    }
}
