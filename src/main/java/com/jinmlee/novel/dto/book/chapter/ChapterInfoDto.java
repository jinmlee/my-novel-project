package com.jinmlee.novel.dto.book.chapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ChapterInfoDto {
    private Long chapterId;
    private String  title;
    private String content;
    private Long bookId;
    private String bookName;
}
