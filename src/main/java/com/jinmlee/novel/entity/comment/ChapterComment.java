package com.jinmlee.novel.entity.comment;

import com.jinmlee.novel.entity.Book.Chapter;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChapterComment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Lob
    @Size(max = 300)
    private String comment;

    @OneToMany(mappedBy = "chapterComment")
    private Set<CommentReaction> commentReactions;
}
