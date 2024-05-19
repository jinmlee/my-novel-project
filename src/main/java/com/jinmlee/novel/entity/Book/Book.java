package com.jinmlee.novel.entity.Book;

import com.jinmlee.novel.dto.book.BookMakeDto;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.base.BaseTimeEntity;
import com.jinmlee.novel.entity.chapter.Chapter;
import com.jinmlee.novel.entity.file.FileEntity;
import com.jinmlee.novel.enums.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Book extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String bookName;

    @Lob
    @Size(min = 0, max = 300)
    private String bookIntroduction;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileEntity bookImg;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Chapter> chapterList = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BookSubscribe> bookSubscribeList = new ArrayList<>();

    public void modify(BookMakeDto bookMakeDto, FileEntity fileEntity){
        this.bookName = bookMakeDto.getBookName();
        this.genre = bookMakeDto.getGenre();
        this.bookIntroduction = bookMakeDto.getBookIntroduction();
        if(fileEntity != null){
            this.bookImg = fileEntity;
        }
    }
}
