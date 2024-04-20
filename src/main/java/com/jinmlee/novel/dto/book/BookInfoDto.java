package com.jinmlee.novel.dto.book;

import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.enums.AgeRating;
import com.jinmlee.novel.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookInfoDto {
    private Long bookId;
    private String author;
    private String bookName;
    private String bookIntro;
    private Genre genre;
    private String bookImg;
}
