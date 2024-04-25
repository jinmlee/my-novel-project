package com.jinmlee.novel.controller;

import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.dto.comment.CommentDto;
import com.jinmlee.novel.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/make")
    @ResponseBody
    public CommentDto makeComment(@RequestParam("comment") String comment,
                                  @RequestParam("chapterId") Long chapterId,
                                  @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        return commentService.makeChapterComment(chapterId, customUserDetails.getMember(), comment);
    }

    @PostMapping("/addReaction")
    @ResponseBody
    public void addReactionPoint(@RequestParam(name = "commentId") Long commentId,
                        @RequestParam(name = "reactionPoint") int reactionPoint,
                        @AuthenticationPrincipal CustomUserDetails customUserDetails){

        commentService.addReaction(commentId, customUserDetails.getMember(), reactionPoint);

    }
}
