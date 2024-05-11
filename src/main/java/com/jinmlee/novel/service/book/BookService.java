package com.jinmlee.novel.service.book;


import com.jinmlee.novel.dto.book.*;
import com.jinmlee.novel.entity.Book.BookSubscribe;
import com.jinmlee.novel.enums.Genre;
import com.jinmlee.novel.repository.Book.BookSubscribeRepository;
import com.jinmlee.novel.utils.FileStore;
import com.jinmlee.novel.dto.auth.CustomUserDetails;
import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.file.FileEntity;
import com.jinmlee.novel.repository.Book.BookRepository;
import com.jinmlee.novel.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookSubscribeRepository bookSubscribeRepository;
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
        Pageable pageable = PageRequest.of(myBookSliceDto.getNumber(), myBookSliceDto.getSize(), Sort.by(Sort.Direction.DESC, "createdDate"));

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


    public boolean isSubscribed(Long bookId, Long memberId){
        return bookSubscribeRepository.existsByBookIdAndMemberId(bookId, memberId);
    }

    public boolean reactionSubscribed(Long bookId, Member member){

        Optional<BookSubscribe> bookSubscribeOpt = bookSubscribeRepository.findByBookIdAndMemberId(bookId, member.getId());

        if(bookSubscribeOpt.isPresent()){
            bookSubscribeRepository.delete(bookSubscribeOpt.get());
            return false;
        }
        Optional<Book> book = bookRepository.findById(bookId);
        if(book.isPresent()) {

            BookSubscribe bookSubscribe = BookSubscribe.builder()
                    .book(book.get())
                    .member(member)
                    .build();

            bookSubscribeRepository.save(bookSubscribe);
        }
        return true;
    }

    public List<BookIndexDto> getRankingList(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "rankingScore"));

        Page<Object[]> rankingList = bookRepository.findRankingList(pageable);
        List<BookIndexDto> test = rankingList.stream().map(result -> new BookIndexDto(
                        (Long) result[0], //bookId
                        (String) result[1], //bookName
                        (String) result[2], // author
                        (Genre) result[3], // genre
                        (String) result[4], // bookImg
                        (Long) result[5] // subscribesCnt
                ))
                .toList();

        return test;
    }
}
