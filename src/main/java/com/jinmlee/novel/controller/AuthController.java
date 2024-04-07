package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.auth.JoinDto;
import com.jinmlee.novel.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String joinProcess(@Validated @ModelAttribute JoinDto joinDto,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "auth/join";
        }

        authService.join(joinDto);
        return "redirect:/auth/login";
    }

    @GetMapping("/idCheck")
    @ResponseBody
    public int idCheck(String userId){
        if(userId == null || userId.isEmpty()){
            return -1;
        }else{
            return authService.idCheck(userId);
        }
    }
}
