package com.jinmlee.novel.repository.Chapter;

import com.jinmlee.novel.entity.chapter.ChapterLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterLikeRepository extends JpaRepository<ChapterLike, Long> {

    Optional<ChapterLike> findByChapterIdAndMemberId(Long chapterId, Long memberId);
}
