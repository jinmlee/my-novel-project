package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.member.CustomUserDetails;
import com.jinmlee.novel.dto.book.BookInfoDto;
import com.jinmlee.novel.dto.book.BookMakeDto;
import com.jinmlee.novel.enums.Genre;
import com.jinmlee.novel.service.book.BookService;
import com.jinmlee.novel.service.book.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final ChapterService chapterService;

    @GetMapping("/make")
    public String make(Model model){

        model.addAttribute("Genre", Genre.values());
        return "book/make";
    }

    @PostMapping("/make")
    public String makeProcess(@ModelAttribute BookMakeDto bookMakeDto,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) throws IOException {

        bookService.bookMake(bookMakeDto, customUserDetails);
        return "redirect:/";
    }

    @GetMapping("/library")
    public String library(Model model, @RequestParam(name = "sortType", required = false, defaultValue = "lastModifiedDate") String sortType,
                          @RequestParam(name = "genre", required = false, defaultValue = "all") String genre,
                          @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
                          @RequestParam(name = "pageNum", defaultValue = "1") int pageNum){


        model.addAttribute("genreList", Genre.values());
        model.addAttribute("bookList", bookService.getBookInfoList(sortType, genre, keyword, pageNum, model));
        model.addAttribute("keyword", keyword);
        model.addAttribute("genre", genre);
        model.addAttribute("sortType", sortType);

        return "book/library";
    }

    @GetMapping("/modify/{bookId}")
    public String modifyBook(@PathVariable(name = "bookId") Long bookId,
                             Model model){
        BookInfoDto bookInfoDto = bookService.getBookInfo(bookId);
        model.addAttribute("Genre", Genre.values());
        model.addAttribute("bookId", bookId);
        model.addAttribute("bookInfo", bookInfoDto);

        return "book/modify";
    }

    @PostMapping("/modify/{bookId}")
    public String modifyBookProcess(@PathVariable(name = "bookId") Long bookId,
                                    @ModelAttribute BookMakeDto bookMakeDto) throws IOException {

        bookService.modifyBook(bookMakeDto, bookId);

        return "redirect:/member/my_book/" + bookId;
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteBook(@RequestParam("bookId") Long bookId,
                                             @AuthenticationPrincipal CustomUserDetails customUserDetails){

        if(!bookService.existsMyBook(bookId, customUserDetails.getMember().getId())){
            throw new IllegalArgumentException("책을 작성한 사람만 삭제가능합니다.");
        }

        bookService.deleteMyBook(bookId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/view/{bookId}")
    public String viewBook(@PathVariable(name = "bookId") Long bookId, Model model,
                           @RequestParam(name = "chapterSortType", defaultValue = "DESC") String chapterSortType,
                           @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                           @AuthenticationPrincipal CustomUserDetails customUserDetails){

        model.addAttribute("chapterSortType", chapterSortType);
        model.addAttribute("bookInfo", bookService.getBookInfo(bookId));
        model.addAttribute("Subscribed", bookService.isSubscribed(bookId, customUserDetails.getMember().getId()));
        model.addAttribute("chapterList", chapterService.getChapterList(bookId, chapterSortType, pageNum, model));
        return "book/view";
    }

    @PostMapping("/addSubscribe")
    @ResponseBody
    public Map<String, Object> addSubscribeProcess(@RequestParam(name = "bookId") Long bookId,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails){

        Map<String, Object> response = new HashMap<>();
        response.put("isSubscribe", bookService.reactionSubscribed(bookId, customUserDetails.getMember()));

        return response;
    }
}
