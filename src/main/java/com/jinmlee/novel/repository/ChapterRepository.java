package com.jinmlee.novel.repository;

import com.jinmlee.novel.entity.Book.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
