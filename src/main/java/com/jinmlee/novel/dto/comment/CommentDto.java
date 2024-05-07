package com.jinmlee.novel.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @JsonProperty("formattedTime")
    public String getFormattedTime(){
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public String getTime(){
        return createdDate.getYear() + "." + createdDate.getMonthValue() + "." + createdDate.getDayOfMonth();
    }
}
