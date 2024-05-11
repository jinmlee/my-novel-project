package com.jinmlee.novel.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MyViewDto {
    private Long bookId;
    private Long chapterId;
    private String author;
    private String bookName;
    private String bookImg;
    private String chapterTitle;
}
