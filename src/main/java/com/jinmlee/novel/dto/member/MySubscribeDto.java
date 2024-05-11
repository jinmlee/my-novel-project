package com.jinmlee.novel.dto.member;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MySubscribeDto {
    private Long bookId;
    private String bookName;
    private String author;
    private String bookImg;

}
