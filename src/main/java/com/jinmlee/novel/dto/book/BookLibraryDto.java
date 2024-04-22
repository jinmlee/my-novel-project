package com.jinmlee.novel.dto.book;

import com.jinmlee.novel.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class BookLibraryDto {
    private Long bookId;
    private String bookName;
    private String author;
    private Genre genre;
    private String bookImg;
}
