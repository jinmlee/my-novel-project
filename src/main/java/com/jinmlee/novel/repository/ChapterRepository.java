package com.jinmlee.novel.repository;

import com.jinmlee.novel.dto.book.chapter.ChapterListDto;
import com.jinmlee.novel.dto.book.chapter.ChapterModifyDto;
import com.jinmlee.novel.entity.Book.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("select new com.jinmlee.novel.dto.book.chapter.ChapterListDto(c.id, c.title) from Chapter c " +
            "where c.book.id = :bookId")
    List<ChapterListDto> findChapterList(@Param("bookId") Long bookId);

    @Query("select new com.jinmlee.novel.dto.book.chapter.ChapterModifyDto(c.id, c.title, c.content, b.bookName) from Chapter c " +
            "join c.book b " +
            "where c.id = :chapterId")
    ChapterModifyDto findChapterModifyInfo(@Param("chapterId") Long chapterId);
}
