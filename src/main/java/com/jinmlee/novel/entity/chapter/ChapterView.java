package com.jinmlee.novel.entity.chapter;

import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(
        name = "chapter_view",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"chapter_id", "member_id"})
        }
)
public class ChapterView extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
