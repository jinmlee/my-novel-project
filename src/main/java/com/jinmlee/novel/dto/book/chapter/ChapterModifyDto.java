package com.jinmlee.novel.dto.book.chapter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ChapterModifyDto {
    private Long chapterId;
    private String title;
    private String content;
    private String bookName;
}
