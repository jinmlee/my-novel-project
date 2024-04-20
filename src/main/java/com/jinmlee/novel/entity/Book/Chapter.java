package com.jinmlee.novel.entity.Book;

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
public class Chapter {

    @Id @GeneratedValue
    @Column(name = "chapter_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    @Lob
    @Size(min = 0, max = 50000)
    private String content;

    private long hits;
}
