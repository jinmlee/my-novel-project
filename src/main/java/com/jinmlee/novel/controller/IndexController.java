package com.jinmlee.novel.controller;

import com.jinmlee.novel.service.book.BookService;
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

    private final BookService bookService;

    @GetMapping("/")
    public String index(Model model){



        return "index";
    }
}
