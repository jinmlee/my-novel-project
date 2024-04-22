package com.jinmlee.novel.entity.Book;

import com.jinmlee.novel.dto.book.chapter.ChapterMakeDto;
import com.jinmlee.novel.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chapter extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "chapter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    @Lob
    @Size(min = 0, max = 50000)
    private String content;

    private long hits;

    public void modify(ChapterMakeDto chapterMakeDto){
        this.title = chapterMakeDto.getTitle();
        this.content = chapterMakeDto.getContent();
    }
}
