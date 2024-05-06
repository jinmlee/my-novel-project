package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.dto.book.BookInfoDto;
import com.jinmlee.novel.dto.book.MyBookDto;
import com.jinmlee.novel.dto.book.MyBookSliceDto;
import com.jinmlee.novel.dto.book.chapter.ChapterPageDto;
import com.jinmlee.novel.service.Member.MemberService;
import com.jinmlee.novel.service.book.BookService;
import com.jinmlee.novel.service.book.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final BookService bookService;
    private final ChapterService chapterService;

    @GetMapping("/my_info")
    public String myInfo(){

        return "member/my_info";
    }

    @GetMapping("/my_book")
    public String myBook(Model model,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails,
                         @ModelAttribute MyBookSliceDto myBookSliceDto){

        long memberId = customUserDetails.getMember().getId();

        List<MyBookDto> myBookList = bookService.getMyBookList(memberId, myBookSliceDto);

        model.addAttribute("myBookList", myBookList);
        model.addAttribute("myBookSliceDto", myBookSliceDto);
        return "member/my_book";
    }

    @GetMapping("/my_book/more")
    @ResponseBody
    public Map<String, Object> myBookMore(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                          @RequestParam("pageNumber") int pageNumber) {
        long memberId = customUserDetails.getMember().getId();
        MyBookSliceDto myBookSliceDto = new MyBookSliceDto();
        myBookSliceDto.setNumber(pageNumber);
        List<MyBookDto> myBookList = bookService.getMyBookList(memberId, myBookSliceDto);
        Map<String, Object> response = new HashMap<>();
        response.put("myBookList", myBookList);
        response.put("myBookSliceDto", myBookSliceDto);
        return response;
    }


    @GetMapping("/my_book/{bookId}")
    public String myBookInfo(@PathVariable(name="bookId") Long bookId, Model model,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             @RequestParam(name = "chapterSortType", defaultValue = "DESC") String chapterSortType,
                             @RequestParam(name = "pageNum", defaultValue = "1") int pageNum){
        if(bookService.existsMyBook(bookId, customUserDetails.getMember().getId())){
            model.addAttribute("myBookInfo", bookService.getBookInfo(bookId));
            model.addAttribute("chapterList", chapterService.getChapterList(bookId, chapterSortType, pageNum, model));
            model.addAttribute("chapterSortType", chapterSortType);
        }else {
            throw new IllegalArgumentException("책을 작성한 사람만 접속할 수 있습니다.");
        }
        return "member/my_book_info";
    }
}
