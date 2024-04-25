package com.jinmlee.novel.repository;

import com.jinmlee.novel.entity.comment.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    Optional<CommentReaction> findByChapterCommentIdAndMemberId(Long commentId, Long memberId);
}
