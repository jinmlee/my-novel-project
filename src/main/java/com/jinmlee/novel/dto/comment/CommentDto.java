package com.jinmlee.novel.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CommentDto {
    private Long commentId;
    private String userName;
    private String comment;
    private Long goodPoint;
    private Long badPoint;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer loggedReaction;
}
