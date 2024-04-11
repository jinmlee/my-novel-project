package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.dto.book.BookMakeDto;
import com.jinmlee.novel.enums.AgeRating;
import com.jinmlee.novel.enums.Genre;
import com.jinmlee.novel.service.book.BookService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {

        bookService.bookMake(bookMakeDto, customUserDetails);
        return "redirect:/";
    }

    @GetMapping("/library")
    public String library(){



        return "book/library";
    }
}
