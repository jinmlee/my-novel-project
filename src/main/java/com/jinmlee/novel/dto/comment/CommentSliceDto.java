package com.jinmlee.novel.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CommentSliceDto {
    private int size = 10;
    private int number;
    private String sortType = "goodPoint";
    private boolean hasNext;

    public void set(Slice<Object[]> commentDtoSlice){
        this.number = commentDtoSlice.getNumber();
        this.hasNext = commentDtoSlice.hasNext();
    }
}
