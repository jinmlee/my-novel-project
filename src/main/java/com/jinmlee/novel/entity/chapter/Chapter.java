package com.jinmlee.novel.entity.chapter;

import com.jinmlee.novel.dto.book.chapter.ChapterMakeDto;
import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.entity.base.BaseTimeEntity;
import com.jinmlee.novel.entity.comment.ChapterComment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_book_id", columnList = "book_id"),
})
public class Chapter extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "chapter_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    private Long hits;

    @Lob
    @Size(min = 0, max = 50000)
    private String content;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChapterComment> chapterCommentList = new ArrayList<>();


    public void modify(ChapterMakeDto chapterMakeDto){
        this.title = chapterMakeDto.getTitle();
        this.content = chapterMakeDto.getContent();
    }
}
