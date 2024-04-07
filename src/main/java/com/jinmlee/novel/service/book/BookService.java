package com.jinmlee.novel.service.book;


import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.dto.book.BookMakeDto;
import com.jinmlee.novel.dto.book.MyBookDto;
import com.jinmlee.novel.dto.book.MyBookSliceDto;
import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void bookMake(BookMakeDto bookMakeDto, CustomUserDetails customUserDetails){

        Member member = customUserDetails.getMember();
        Book book = Book.builder()
                .bookName(bookMakeDto.getBookName())
                .bookIntroduction(bookMakeDto.getBookIntroduction())
                .genre(bookMakeDto.getGenre())
                .ageRating(bookMakeDto.getAgeRating())
                .member(member)
                .build();

        bookRepository.save(book);
    }

    public List<MyBookDto> getMyBookList(long memberId, MyBookSliceDto myBookSliceDto){
        Pageable pageable = PageRequest.of(myBookSliceDto.getNumber(), myBookSliceDto.getSize(), Sort.by(Sort.Direction.DESC, "id"));

        Slice<MyBookDto> myBookDtoSlice = bookRepository.findMyBookList(memberId, pageable);
        myBookSliceDto.set(myBookDtoSlice);

        return myBookDtoSlice.getContent();

    }
}
