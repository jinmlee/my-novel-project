package com.jinmlee.novel.entity.chapter;

import com.jinmlee.novel.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(
        name = "chapter_like",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"chapter_id", "member_id"})
        }
)
public class ChapterLike {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
