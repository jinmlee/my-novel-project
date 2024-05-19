package com.jinmlee.novel.repository.Book;

import com.jinmlee.novel.dto.book.BookIndexDto;
import com.jinmlee.novel.dto.book.BookInfoDto;
import com.jinmlee.novel.dto.book.MyBookDto;
import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.enums.Genre;
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

    @Query("select new com.jinmlee.novel.dto.book.BookInfoDto(b.id, m.nickname, b.bookName, b.bookIntroduction, b.genre, i.storeFileName, count(bs)) " +
            "from Book b " +
            "join b.member m " +
            "left join b.bookImg i " +
            "left join BookSubscribe bs on bs.book = b " +
            "where b.id = :bookId " +
            "group by b")
    BookInfoDto findBookInfo(@Param("bookId") Long bookId);


    @Query("select new com.jinmlee.novel.dto.book.BookInfoDto(b.id, m.nickname, b.bookName, b.bookIntroduction, b.genre, i.storeFileName, count(distinct bs)),count(distinct bs) as subscribe , coalesce(sum (cl.hits), 0) as hits " +
            "from Book b " +
            "join b.member m " +
            "left join b.bookImg i " +
            "left join BookSubscribe bs on bs.book = b " +
            "left join b.chapterList cl " +
            "where (:genre is null or b.genre = :genre) and (b.bookName like lower(concat('%', :keyword, '%')) or m.nickname like lower(concat('%', :keyword, '%'))) " +
            "group by b")
    Page<BookInfoDto> findBookInfoList(@Param("keyword") String keyword, @Param("genre") Genre genre, Pageable pageable);



    @Query("select b.bookName from Book b where b.id = :bookId")
    String findBookName(@Param("bookId") Long bookId);


    @Query("select b.id, b.bookName, m.nickname, b.genre, i.storeFileName, count(distinct bs) as subscribesCnt, (count(distinct bs) + COALESCE(AVG(c.hits), 0)) AS rankingScore from Book b " +
            "join b.member m left join BookSubscribe bs on bs.book = b left join Chapter c on c.book = b " +
            "left join b.bookImg i " +
            "group by b.id")
    Page<Object[]> findRankingList(Pageable pageable);
}
