package com.jinmlee.novel.repository.Book;

import com.jinmlee.novel.dto.member.MySubscribeDto;
import com.jinmlee.novel.entity.Book.BookSubscribe;
import com.jinmlee.novel.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookSubscribeRepository extends JpaRepository<BookSubscribe, Long> {

    boolean existsByBookIdAndMemberId(Long bookId, Long MemberId);

    Optional<BookSubscribe> findByBookIdAndMemberId(Long bookId, Long memberId);

    @Query("select new com.jinmlee.novel.dto.member.MySubscribeDto(b.id, b.bookName, b.member.nickname, i.storeFileName) from BookSubscribe bs " +
            "join bs.book b " +
            "left join b.bookImg i " +
            "where bs.member = :member")
    Slice<MySubscribeDto> findMySubscribeList(@Param("member") Member member, Pageable pageable);
}
