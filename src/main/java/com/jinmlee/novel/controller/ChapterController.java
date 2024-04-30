package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.dto.book.chapter.ChapterMakeDto;
import com.jinmlee.novel.dto.comment.CommentSliceDto;
import com.jinmlee.novel.service.book.ChapterService;
import com.jinmlee.novel.service.book.BookService;
import com.jinmlee.novel.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final CommentService commentService;

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

    @GetMapping("/modify/{bookId}/{chapterId}")
    public String modify( @PathVariable(name = "bookId") Long bookId, @PathVariable(name = "chapterId") Long chapterId, Model model,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails){

        if (bookService.existsMyBook(bookId, customUserDetails.getMember().getId())) {
            model.addAttribute("chapter", chapterService.getChapterModifyDto(chapterId));
        } else {
            throw new IllegalArgumentException("책을 작성한 사람만 접속할 수 있습니다.");
        }

        return "/chapter/modify";
    }

    @PostMapping("/modify/{chapterId}")
    public String modifyProcess(@PathVariable Long chapterId,
                                @ModelAttribute ChapterMakeDto chapterMakeDto){

        chapterService.modifyChapter(chapterMakeDto, chapterId);

        return "redirect:/member/my_book";
    }

    @GetMapping("/view/{chapterId}")
    public String viewChapter(@PathVariable(name = "chapterId") Long chapterId,
                              Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        CommentSliceDto commentSliceDto = new CommentSliceDto();
        model.addAttribute("chapterInfo", chapterService.getChapterInfo(chapterId));
        model.addAttribute("loggedNickname",  customUserDetails.getMember().getNickname());
        model.addAttribute("commentList", commentService.getCommentList(chapterId, customUserDetails.getMember().getId(), commentSliceDto));
        model.addAttribute("commentSliceDto", commentSliceDto);

        return "chapter/view";
    }
}
