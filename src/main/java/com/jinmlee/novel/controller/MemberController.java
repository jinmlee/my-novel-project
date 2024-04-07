package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.dto.book.MyBookDto;
import com.jinmlee.novel.dto.book.MyBookSliceDto;
import com.jinmlee.novel.service.Member.MemberService;
import com.jinmlee.novel.service.book.BookService;
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
}
