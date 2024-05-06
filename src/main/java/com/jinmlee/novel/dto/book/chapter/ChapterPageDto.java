package com.jinmlee.novel.dto.book.chapter;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ChapterPageDto {
    int pageGroupSize = 5;
    int pageSize = 10;
    int pageNum;
    int startPage;
    int endPage;
    int totalPage;
    int pageGroup;
    int nextGroup;
    int previousGroup;

    public ChapterPageDto(int pageNum){
        if(pageNum < 1){
            pageNum = 1;
        }
        this.pageNum = pageNum;
    }

    public void set(Page<ChapterListDto> chapterList){
        this.pageNum = chapterList.getNumber() + 1;
        this.totalPage = chapterList.getTotalPages();
        this.pageGroup = (int)Math.ceil((double) pageNum / pageGroupSize);
        this.startPage = ((pageGroup - 1) * pageGroupSize) + 1;
        this.endPage = pageGroup * pageGroupSize;
        if(endPage > totalPage){
            endPage = totalPage;
        }
    }

    public int getNextGroup(){
        if(pageGroup * pageGroupSize + 1 <= totalPage){
            return pageGroup * pageGroupSize + 1;
        }else {
            return totalPage;
        }
    }

    public int getPreviousGroup(){
        if((pageGroup - 1) * pageGroupSize > 0){
            return (pageGroup - 1) * pageGroupSize;
        }else {
            return 1;
        }
    }
}
