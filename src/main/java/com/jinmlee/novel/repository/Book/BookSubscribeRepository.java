package com.jinmlee.novel.repository.Book;

import com.jinmlee.novel.entity.Book.BookSubscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookSubscribeRepository extends JpaRepository<BookSubscribe, Long> {

    boolean existsByBookIdAndMemberId(Long bookId, Long MemberId);

    Optional<BookSubscribe> findByBookIdAndMemberId(Long bookId, Long memberId);
}
