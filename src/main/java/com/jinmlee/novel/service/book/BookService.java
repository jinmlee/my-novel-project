package com.jinmlee.novel.service.book;


import com.jinmlee.novel.dto.book.BookInfoDto;
import com.jinmlee.novel.utils.FileStore;
import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.dto.book.BookMakeDto;
import com.jinmlee.novel.dto.book.MyBookDto;
import com.jinmlee.novel.dto.book.MyBookSliceDto;
import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.file.FileEntity;
import com.jinmlee.novel.repository.BookRepository;
import com.jinmlee.novel.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final FileRepository fileRepository;
    private final FileStore fileStore;

    public void bookMake(BookMakeDto bookMakeDto, CustomUserDetails customUserDetails) throws IOException {

        Member member = customUserDetails.getMember();

        FileEntity fileEntity = fileStore.storeFile(bookMakeDto.getBookImg());

        if(fileEntity != null) {
            fileRepository.save(fileEntity);
        }

        Book book = Book.builder()
                .bookName(bookMakeDto.getBookName())
                .bookIntroduction(bookMakeDto.getBookIntroduction())
                .genre(bookMakeDto.getGenre())
                .member(member)
                .bookImg(fileEntity)
                .build();

        bookRepository.save(book);
    }

    public List<MyBookDto> getMyBookList(long memberId, MyBookSliceDto myBookSliceDto){
        Pageable pageable = PageRequest.of(myBookSliceDto.getNumber(), myBookSliceDto.getSize(), Sort.by(Sort.Direction.DESC, "id"));

        Slice<MyBookDto> myBookDtoSlice = bookRepository.findMyBookList(memberId, pageable);
        myBookSliceDto.set(myBookDtoSlice);

        return myBookDtoSlice.getContent();

    }

    public boolean existsMyBook(Long bookId, Long userId){
        return bookRepository.existsByIdAndMemberId(bookId, userId);
    }

    public BookInfoDto getBookInfo(Long bookId){
        return bookRepository.findBookInfo(bookId);
    }

    public List<BookInfoDto> getBookInfoList(){
        return bookRepository.findBookInfoList();
    }

    public void modifyBook(BookMakeDto bookMakeDto, Long bookId) throws IOException {
        Book book = bookRepository.findById(bookId).get();
        FileEntity fileEntity = fileStore.storeFile(bookMakeDto.getBookImg());
        if(fileEntity != null){
            fileRepository.save(fileEntity);
        }
        book.modify(bookMakeDto, fileEntity);
        bookRepository.save(book);
    }

    public String getBookName(Long bookId){
        return bookRepository.findBookName(bookId);
    }
}
