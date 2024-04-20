package com.jinmlee.novel.service.book;

import com.jinmlee.novel.dto.book.ChapterMakeDto;
import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.entity.Book.Chapter;
import com.jinmlee.novel.repository.BookRepository;
import com.jinmlee.novel.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;
    public void makeChapter(ChapterMakeDto chapterMakeDto, Long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        Chapter chapter = chapterMakeDto.toEntity(book.get());
        chapterRepository.save(chapter);
    }
}
