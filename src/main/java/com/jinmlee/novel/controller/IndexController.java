package com.jinmlee.novel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

    @GetMapping("/")
    public String index(Model model){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("id", id);
        return "index";
    }
}
