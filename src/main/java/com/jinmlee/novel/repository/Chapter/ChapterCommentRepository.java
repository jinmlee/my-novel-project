package com.jinmlee.novel.repository.Chapter;


import com.jinmlee.novel.dto.comment.CommentDto;
import com.jinmlee.novel.entity.comment.ChapterComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterCommentRepository extends JpaRepository<ChapterComment, Long> {

    @Query("select c.id, m.nickname, c.comment," +
            "SUM(CASE WHEN cr.reactionPoint = 1 THEN 1 ELSE 0 END) AS goodPoint, " +
            "SUM(CASE WHEN cr.reactionPoint = -1 THEN 1 ELSE 0 END) AS badPoint, " +
            "c.createdDate, c.lastModifiedDate, " +
            "(SELECT cr.reactionPoint FROM CommentReaction cr WHERE cr.chapterComment = c AND cr.member.id = :loggedId) AS loggedReaction " +
            "from ChapterComment c " +
            "join c.member m left join CommentReaction cr on cr.chapterComment = c " +
            "where c.chapter.id = :chapterId " +
            "group by c.id")
    public Slice<Object[]> findCommentList(@Param("chapterId") Long chapterId, @Param("loggedId") Long loggedId, Pageable pageable);
}
