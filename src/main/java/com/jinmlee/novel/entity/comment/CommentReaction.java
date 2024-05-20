package com.jinmlee.novel.entity.comment;

import com.jinmlee.novel.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(
        name = "comment_reaction",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"comment_id", "member_id"})
        },
        indexes = {
                @Index(name = "idx_comment_id_member_id", columnList = "comment_id, member_id"),
        }
)
public class CommentReaction {

    @Id @GeneratedValue
    @Column(name = "comment_reaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private ChapterComment chapterComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer reactionPoint;
}
