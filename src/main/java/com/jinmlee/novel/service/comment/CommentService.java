package com.jinmlee.novel.service.comment;

import com.jinmlee.novel.dto.comment.CommentDto;
import com.jinmlee.novel.dto.comment.CommentSliceDto;
import com.jinmlee.novel.entity.chapter.Chapter;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.comment.ChapterComment;
import com.jinmlee.novel.entity.comment.CommentReaction;
import com.jinmlee.novel.repository.ChapterCommentRepository;
import com.jinmlee.novel.repository.ChapterRepository;
import com.jinmlee.novel.repository.CommentReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<CommentDto> getCommentList(Long chapterId, Long loggedId, CommentSliceDto commentSliceDto) {

        Slice<Object[]> results = chapterCommentRepository.findCommentList(chapterId, loggedId, getPageable(commentSliceDto));
        commentSliceDto.set(results);

        return results.stream().map(result -> new CommentDto(
                (Long) result[0],
                (String) result[1],
                (String) result[2],
                (Long)(result[3]),
                (Long)(result[4]),
                (LocalDateTime) result[5],
                (LocalDateTime) result[6],
                (Integer) (result[7]))
        ).collect(Collectors.toList());
    }

    private Pageable getPageable(CommentSliceDto commentSliceDto){
        return PageRequest.of(commentSliceDto.getNumber(), commentSliceDto.getSize(), getSortType(commentSliceDto.getSortType()));
    }

    private Sort getSortType(String sortType){
        if(sortType.contains("goodPoint")){
            return Sort.by(Sort.Direction.DESC, sortType);
        }
        return Sort.by(Sort.Direction.DESC, "createdDate");
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
            throw new IllegalArgumentException("잘못된 접근입니다.");
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
