package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.CustomUserDetails;
import com.jinmlee.novel.dto.book.BookMakeDto;
import com.jinmlee.novel.entity.Book;
import com.jinmlee.novel.enums.AgeRating;
import com.jinmlee.novel.enums.Genre;
import com.jinmlee.novel.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/make")
    public String make(Model model){

        model.addAttribute("Genre", Genre.values());
        model.addAttribute("AgeRating", AgeRating.values());
        return "book/make";
    }

    @PostMapping("/make")
    public String makeProcess(@ModelAttribute BookMakeDto bookMakeDto,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails){
        bookService.bookMake(bookMakeDto, customUserDetails);
        return "redirect:/";
    }
}
