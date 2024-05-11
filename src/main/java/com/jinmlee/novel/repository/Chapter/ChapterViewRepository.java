package com.jinmlee.novel.repository.Chapter;

import com.jinmlee.novel.dto.member.MyViewDto;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.chapter.Chapter;
import com.jinmlee.novel.entity.chapter.ChapterView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ChapterViewRepository extends JpaRepository<ChapterView, Long> {

    Optional<ChapterView> findByChapterAndMember(Chapter chapter, Member member);


    @Query("SELECT new com.jinmlee.novel.dto.member.MyViewDto(b.id, c.id, b.member.nickname, b.bookName, i.storeFileName, c.title) " +
            "FROM ChapterView cv " +
            "JOIN cv.chapter c " +
            "JOIN c.book b " +
            "LEFT JOIN b.bookImg i " +
            "WHERE cv.member = :member AND cv.lastModifiedDate = " +
            "(SELECT MAX(cv2.lastModifiedDate) FROM ChapterView cv2 WHERE cv2.member = :member AND cv2.chapter.book = b)")
    Slice<MyViewDto> findMyViewList(@Param("member") Member member, Pageable pageable);

    @Modifying
    @Query("delete ChapterView cv " +
            "where cv.member = :member AND cv.chapter.book.id = :bookId")
    void deleteChapterViewByMemberAndBookId(@Param("member") Member member, @Param("bookId") Long bookId);
}
