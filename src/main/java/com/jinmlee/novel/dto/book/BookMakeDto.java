package com.jinmlee.novel.dto.book;

import com.jinmlee.novel.enums.AgeRating;
import com.jinmlee.novel.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookMakeDto {
    private String bookName;
    private String bookIntroduction;
    private Genre genre;
    private AgeRating ageRating;
    private MultipartFile bookImg;
}
