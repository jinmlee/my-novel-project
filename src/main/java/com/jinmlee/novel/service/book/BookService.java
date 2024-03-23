package com.jinmlee.novel.service.book;


import com.jinmlee.novel.dto.CustomUserDetails;
import com.jinmlee.novel.dto.book.BookMakeDto;
import com.jinmlee.novel.entity.Book;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.repository.BookRepository;
import com.jinmlee.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void bookMake(BookMakeDto bookMakeDto, CustomUserDetails customUserDetails){

        System.out.println("@@@@@@@@@@오류 첫번쨰");
        Member member = customUserDetails.getMember();
        System.out.println("@@@@@@@@@@@@@22222");
        Book book = Book.builder()
                .bookName(bookMakeDto.getBookName())
                .bookIntroduction(bookMakeDto.getBookIntroduction())
                .genre(bookMakeDto.getGenre())
                .ageRating(bookMakeDto.getAgeRating())
                .member(member)
                .build();

        bookRepository.save(book);
    }
}
