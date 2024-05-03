package com.jinmlee.novel.service.book;

import com.jinmlee.novel.dto.book.chapter.ChapterInfoDto;
import com.jinmlee.novel.dto.book.chapter.ChapterListDto;
import com.jinmlee.novel.dto.book.chapter.ChapterMakeDto;
import com.jinmlee.novel.dto.book.chapter.ChapterModifyDto;
import com.jinmlee.novel.entity.Book.Book;
import com.jinmlee.novel.entity.chapter.Chapter;
import com.jinmlee.novel.repository.BookRepository;
import com.jinmlee.novel.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    public void makeChapter(ChapterMakeDto chapterMakeDto, Long bookId){
        Optional<Book> book = bookRepository.findById(bookId);
        chapterMakeDto.setContent(formatNewLines(chapterMakeDto.getContent()));
        Chapter chapter = chapterMakeDto.toEntity(book.get());
        chapterRepository.save(chapter);
    }
    public String formatNewLines(String content) {
        return content.replace("\n", "<br/>");
    }

    public List<ChapterListDto> getChapterList(Long bookId){
        return chapterRepository.findChapterList(bookId);
    }

    public ChapterModifyDto getChapterModifyDto(Long chapterId){
        return chapterRepository.findChapterModifyInfo(chapterId);
    }

    public void modifyChapter(ChapterMakeDto chapterMakeDto, Long chapterId){
        Optional<Chapter> findChapter = chapterRepository.findById(chapterId);
        if(findChapter.isPresent()){
            Chapter chapter = findChapter.get();
            chapter.modify(chapterMakeDto);
            chapterRepository.save(chapter);
        }
    }

    public ChapterInfoDto getChapterInfo(Long chapterId, Long memberId){

        ChapterInfoDto chapterInfoDto = chapterRepository.findChapterInfo(chapterId);
        incrementViewCount(chapterId, memberId);

        return chapterInfoDto;
    }

    public void incrementViewCount(Long chapterId, Long memberId){
        String key = "chapter:hits:" + chapterId + ":" + memberId;

        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            redisTemplate.opsForValue().increment(key);
        }
    }

    @Scheduled(fixedDelay = 30000)
    public void updateViewCount(){
        Set<String> keys = redisTemplate.keys("chapter:hits:*:*");
        if(keys == null)
            return;

        Map<Long, Long> chapterHits = new HashMap<>();
        for(String key: keys){
            Long chapterId = Long.parseLong(key.split(":")[2]);
            chapterHits.put(chapterId, chapterHits.getOrDefault(chapterId, 0L) + 1);
        }

        for(Map.Entry<Long, Long> entry : chapterHits.entrySet()){
            Long chapterId = entry.getKey();
            Long hits = entry.getValue();
            chapterRepository.updateHits(chapterId, hits);
        }

        redisTemplate.delete(keys);
    }
}
