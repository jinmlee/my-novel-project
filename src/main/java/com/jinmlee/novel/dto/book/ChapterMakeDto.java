package com.jinmlee.novel.dto.book;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterMakeDto {
    private Long bookId;
    private String content;
    private String title;
}
