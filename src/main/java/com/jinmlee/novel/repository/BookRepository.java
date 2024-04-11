package com.jinmlee.novel.repository;

import com.jinmlee.novel.dto.book.MyBookDto;
import com.jinmlee.novel.entity.Book.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.jinmlee.novel.dto.book.MyBookDto(b.id, b.bookName, i.storeFileName) " +
            "from Book b join b.member m " +
            "left join b.bookImg i " +
            "where m.id = :loggedId")
    Slice<MyBookDto> findMyBookList(@Param("loggedId") Long loggedId, Pageable pageable);
}
