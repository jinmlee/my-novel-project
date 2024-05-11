package com.jinmlee.novel.repository.Book;

import com.jinmlee.novel.dto.book.BookIndexDto;
import com.jinmlee.novel.dto.book.BookInfoDto;
import com.jinmlee.novel.dto.book.MyBookDto;
import com.jinmlee.novel.entity.Book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.jinmlee.novel.dto.book.MyBookDto(b.id, b.bookName, i.storeFileName) " +
            "from Book b join b.member m " +
            "left join b.bookImg i " +
            "where m.id = :loggedId")
    Slice<MyBookDto> findMyBookList(@Param("loggedId") Long loggedId, Pageable pageable);

    @Query("SELECT COUNT(b) > 0 FROM Book b WHERE b.id = :bookId AND b.member.id = :userId")
    boolean existsByIdAndMemberId(@Param("bookId") Long bookId, @Param("userId") Long userId);

    @Query("select new com.jinmlee.novel.dto.book.BookInfoDto(b.id, m.nickname, b.bookName, b.bookIntroduction, b.genre, i.storeFileName) " +
            "from Book b " +
            "join b.member m " +
            "left join b.bookImg i " +
            "where b.id = :bookId")
    BookInfoDto findBookInfo(@Param("bookId") Long bookId);


    @Query("select new com.jinmlee.novel.dto.book.BookInfoDto(b.id, m.nickname, b.bookName, b.bookIntroduction, b.genre, i.storeFileName) " +
            "from Book b " +
            "join b.member m " +
            "left join b.bookImg i")
    List<BookInfoDto> findBookInfoList();

    @Query("select b.bookName from Book b where b.id = :bookId")
    String findBookName(@Param("bookId") Long bookId);


    @Query("select b.id, b.bookName, m.nickname, b.genre, i.storeFileName, count(distinct bs) as subscribesCnt, (count(distinct bs) + COALESCE(AVG(c.hits), 0)) AS rankingScore from Book b " +
            "join b.member m left join BookSubscribe bs on bs.book = b left join Chapter c on c.book = b " +
            "left join b.bookImg i " +
            "group by b.id")
    Page<Object[]> findRankingList(Pageable pageable);


}
