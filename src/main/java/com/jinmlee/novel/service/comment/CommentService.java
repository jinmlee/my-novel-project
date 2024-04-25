package com.jinmlee.novel.service.comment;

import com.jinmlee.novel.dto.comment.CommentDto;
import com.jinmlee.novel.entity.Book.Chapter;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.comment.ChapterComment;
import com.jinmlee.novel.entity.comment.CommentReaction;
import com.jinmlee.novel.repository.ChapterCommentRepository;
import com.jinmlee.novel.repository.ChapterRepository;
import com.jinmlee.novel.repository.CommentReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ChapterCommentRepository chapterCommentRepository;
    private final ChapterRepository chapterRepository;
    private final CommentReactionRepository commentReactionRepository;

    public CommentDto makeChapterComment(Long chapterID, Member member, String comment) {

        Optional<Chapter> chapter = chapterRepository.findById(chapterID);

        if (chapter.isPresent()) {
            ChapterComment chapterComment = ChapterComment.builder()
                    .chapter(chapter.get())
                    .member(member)
                    .comment(comment)
                    .build();
            ChapterComment saveComment = chapterCommentRepository.save(chapterComment);
            return CommentDto.builder()
                    .comment(saveComment.getComment())
                    .commentId(saveComment.getId())
                    .createdDate(saveComment.getCreatedDate())
                    .lastModifiedDate(saveComment.getLastModifiedDate())
                    .userName(member.getNickname())
                    .build();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<CommentDto> getCommentList(Long chapterId, Long loggedId) {
        return chapterCommentRepository.findCommentList(chapterId, loggedId);
    }

    public void addReaction(Long commentId, Member member, int reactionPoint) {

        Optional<ChapterComment> comment = chapterCommentRepository.findById(commentId);
        Optional<CommentReaction> findCommentReaction = commentReactionRepository.findByChapterCommentIdAndMemberId(commentId, member.getId());

        if (comment.isPresent()) {
            if (findCommentReaction.isPresent()) {
                CommentReaction commentReaction = findCommentReaction.get();
                removeReaction(commentReaction, reactionPoint);
            } else {
                CommentReaction commentReaction = CommentReaction.builder()
                        .chapterComment(comment.get())
                        .member(member)
                        .reactionPoint(reactionPoint)
                        .build();
                commentReactionRepository.save(commentReaction);
            }
        }else {
            throw new IllegalArgumentException();
        }

    }
    public void removeReaction(CommentReaction commentReaction, int reactionPoint) {

        if (reactionPoint != commentReaction.getReactionPoint()) {
            if (reactionPoint == 1)
                throw new IllegalArgumentException("'싫어요'를 한 댓글입니다. 취소 후 요청해 주세요");
            else if (reactionPoint == -1)
                throw new IllegalArgumentException("'좋아요'를 한 댓글입니다. 취소 후 요청해 주세요");
        }
        commentReactionRepository.delete(commentReaction);
    }
}
