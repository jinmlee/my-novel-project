package com.jinmlee.novel.dto.book.chapter;

import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.entity.chapter.Chapter;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterMakeDto {
    private String content;
    private String title;

    public Chapter toEntity(Book book){
        return Chapter.builder()
                .title(this.title)
                .content(this.content)
                .book(book)
                .hits(0L)
                .build();
    }
}
