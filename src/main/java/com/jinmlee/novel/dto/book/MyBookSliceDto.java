package com.jinmlee.novel.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MyBookSliceDto {
    private int size = 10;
    private int number;
    private boolean hasNext;

    public void set(Slice<MyBookDto> myBookDtoSlice){
        this.number = myBookDtoSlice.getNumber();
        this.hasNext = myBookDtoSlice.hasNext();
    }
}
