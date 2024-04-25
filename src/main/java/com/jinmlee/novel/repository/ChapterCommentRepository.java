package com.jinmlee.novel.repository;


import com.jinmlee.novel.dto.comment.CommentDto;
import com.jinmlee.novel.entity.comment.ChapterComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterCommentRepository extends JpaRepository<ChapterComment, Long> {


    @Query("SELECT new com.jinmlee.novel.dto.comment.CommentDto(c.id, m.nickname, c.comment, " +
            "sum (case when cl.reactionPoint = 1 then 1 else 0 end) , sum (case when cl.reactionPoint = -1 then 1 else 0 end ) , c.createdDate, c.lastModifiedDate, " +
            "(select cr.reactionPoint from CommentReaction cr where cr.chapterComment = c and cr.member.id = :loggedId)) " +
            "FROM ChapterComment c " +
            "JOIN c.member m " +
            "LEFT JOIN c.commentReactions cl " +
            "WHERE c.chapter.id = :chapterId " +
            "group by c.id")
    public List<CommentDto> findCommentList(@Param("chapterId") Long chapterId, @Param("loggedId") Long loggedId);
}
