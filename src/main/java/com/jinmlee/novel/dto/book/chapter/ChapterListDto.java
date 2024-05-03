package com.jinmlee.novel.dto.book.chapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChapterListDto {
    private Long id;
    private String title;
    private Long hits;
    private LocalDateTime createdDate;

    public String getTime(){
        return createdDate.getYear() + "." + createdDate.getMonthValue() + "." + createdDate.getDayOfMonth();
    }
}
