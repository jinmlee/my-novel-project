package com.jinmlee.novel.service.book;

import com.jinmlee.novel.dto.book.chapter.ChapterListDto;
import com.jinmlee.novel.dto.book.chapter.ChapterMakeDto;
import com.jinmlee.novel.dto.book.chapter.ChapterModifyDto;
import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.entity.Book.Chapter;
import com.jinmlee.novel.repository.BookRepository;
import com.jinmlee.novel.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<ChapterListDto> getChapterList(Long bookId){
        return chapterRepository.findChapterList(bookId);
    }

    public ChapterModifyDto getChapterModifyDto(Long chapterId){
        return chapterRepository.findChapterModifyInfo(chapterId);
    }

    public void modifyChapter(ChapterMakeDto chapterMakeDto, Long chapterId){
        Optional<Chapter> findChapter = chapterRepository.findById(chapterId);
        Chapter chapter = null;
        if(findChapter.isPresent()){
            chapter = findChapter.get();
            chapter.modify(chapterMakeDto);
            chapterRepository.save(chapter);
        }
    }
}
