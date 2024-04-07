package com.jinmlee.novel.dto.book;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyBookDto {
    private long bookId;
    private String bookName;
}
