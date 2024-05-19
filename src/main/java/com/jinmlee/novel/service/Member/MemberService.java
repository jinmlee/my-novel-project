package com.jinmlee.novel.service.Member;

import com.jinmlee.novel.dto.member.JoinDto;
import com.jinmlee.novel.dto.member.MemberModifyDto;
import com.jinmlee.novel.dto.member.MySubscribeDto;
import com.jinmlee.novel.dto.member.MyViewDto;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.entity.chapter.Chapter;
import com.jinmlee.novel.entity.chapter.ChapterView;
import com.jinmlee.novel.repository.Book.BookSubscribeRepository;
import com.jinmlee.novel.repository.Chapter.ChapterRepository;
import com.jinmlee.novel.repository.Chapter.ChapterViewRepository;
import com.jinmlee.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ChapterViewRepository chapterViewRepository;
    private final ChapterRepository chapterRepository;
    private final BookSubscribeRepository bookSubscribeRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinDto getMyInfo(Long memberId){

        Optional<Member> memberOpt = memberRepository.findById(memberId);

        if(memberOpt.isEmpty()){
            throw new IllegalArgumentException("없는 유저입니다.");
        }

        return new JoinDto().fromEntity(memberOpt.get());

    }

    @Transactional
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

    public Slice<MyViewDto> getMyViewList(Member member, int pageNum){
        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "lastModifiedDate"));
        return chapterViewRepository.findMyViewList(member, pageable);
    }

    @Transactional
    public void removeMyView(Long bookId, Member member){
        chapterViewRepository.deleteChapterViewByMemberAndBookId(member, bookId);
    }

    public Slice<MySubscribeDto> getMySubscribeList(Member member, int pageNum){
        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        return bookSubscribeRepository.findMySubscribeList(member, pageable);
    }

    public void modifyInfo(Member member, MemberModifyDto memberModifyDto){
        member.modify(memberModifyDto);
        memberRepository.save(member);
    }

    public boolean confirmPassword(Member member, String password){
        return bCryptPasswordEncoder.matches(password, member.getPassword());
    }

    @Transactional
    public void modifyPassword(Member member, String password){

        memberRepository.updatePassword(member.getId(), bCryptPasswordEncoder.encode(password));
    }
}
