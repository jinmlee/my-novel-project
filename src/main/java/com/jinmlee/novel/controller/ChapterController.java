package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.dto.book.ChapterMakeDto;
import com.jinmlee.novel.service.book.ChapterService;
import com.jinmlee.novel.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chapter")
public class ChapterController {

    private final ChapterService chapterService;
    private final BookService bookService;

    @GetMapping("/make/{bookId}")
    public String make(@PathVariable(name = "bookId") Long bookId,
                       @AuthenticationPrincipal CustomUserDetails customUserDetails,
                       Model model) {
        if (bookService.existsMyBook(bookId, customUserDetails.getMember().getId())) {
            String bookName = bookService.getBookName(bookId);
            model.addAttribute("bookId", bookId);
            model.addAttribute("bookName", bookName);
        } else {
            throw new IllegalArgumentException("책을 작성한 사람만 접속할 수 있습니다.");
        }

        return "chapter/make";
    }

    @PostMapping("/make/{bookId}")
    public String makeProcess(@PathVariable(name = "bookId") Long bookId,
                              @ModelAttribute ChapterMakeDto chapterMakeDto){
        chapterService.makeChapter(chapterMakeDto, bookId);
        return "redirect:/member/my_book/" + bookId;
    }
}
