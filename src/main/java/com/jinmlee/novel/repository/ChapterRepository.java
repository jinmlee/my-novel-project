package com.jinmlee.novel.repository;

import com.jinmlee.novel.dto.book.chapter.ChapterInfoDto;
import com.jinmlee.novel.dto.book.chapter.ChapterListDto;
import com.jinmlee.novel.dto.book.chapter.ChapterModifyDto;
import com.jinmlee.novel.entity.chapter.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("select new com.jinmlee.novel.dto.book.chapter.ChapterListDto(c.id, c.title, c.hits, c.createdDate) from Chapter c " +
            "where c.book.id = :bookId")
    List<ChapterListDto> findChapterList(@Param("bookId") Long bookId);

    @Query("select new com.jinmlee.novel.dto.book.chapter.ChapterModifyDto(c.id, c.title, c.content, b.bookName) from Chapter c " +
            "join c.book b " +
            "where c.id = :chapterId")
    ChapterModifyDto findChapterModifyInfo(@Param("chapterId") Long chapterId);

    @Query("select new com.jinmlee.novel.dto.book.chapter.ChapterInfoDto(c.id, c.title, c.content, b.id, b.bookName, c.hits) from Chapter c " +
            "join c.book b " +
            "where c.id = :chapterId")
    ChapterInfoDto findChapterInfo(@Param("chapterId") Long chapterId);

    @Modifying
    @Transactional
    @Query("update Chapter c set c.hits = c.hits + :hits where c.id = :chapterId")
    void updateHits(@Param("chapterId") Long chapterId, @Param("hits") Long hits);
}
