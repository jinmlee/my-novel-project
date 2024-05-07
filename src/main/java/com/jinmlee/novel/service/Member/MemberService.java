package com.jinmlee.novel.service.Member;

import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.chapter.Chapter;
import com.jinmlee.novel.entity.chapter.ChapterView;
import com.jinmlee.novel.repository.Chapter.ChapterRepository;
import com.jinmlee.novel.repository.Chapter.ChapterViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ChapterViewRepository chapterViewRepository;
    private final ChapterRepository chapterRepository;

    public void updateMyChapterView(Member member, Long chapterId){

        Optional<Chapter> chapterOp = chapterRepository.findById(chapterId);

        if(chapterOp.isPresent()){
            Optional<ChapterView> chapterViewOp = chapterViewRepository.findByChapterAndMember(chapterOp.get(), member);
            if(chapterViewOp.isPresent()){
                chapterViewOp.get().timeUpdate();
            }else {
                ChapterView chapterView = ChapterView.builder()
                        .chapter(chapterOp.get())
                        .member(member)
                        .build();
                chapterViewRepository.save(chapterView);
            }
        }else {
            throw new IllegalArgumentException();
        }
    }
}
