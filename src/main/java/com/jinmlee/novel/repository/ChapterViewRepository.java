package com.jinmlee.novel.repository;

import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.chapter.Chapter;
import com.jinmlee.novel.entity.chapter.ChapterView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ChapterViewRepository extends JpaRepository<ChapterView, Long> {

    Optional<ChapterView> findByChapterAndMember(Chapter chapter, Member member);
}
