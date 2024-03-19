package com.jinmlee.novel.service;

import com.jinmlee.novel.dto.JoinDto;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public void join(JoinDto joinDto){
        Member dbJoinMember = Member.builder()
                .userId(joinDto.getUserId())
                .nickname(joinDto.getNickname())
                .password(joinDto.getPassword())
                .userName(joinDto.getUserName())
                .email(joinDto.getEmail())
                .phoneNumber(joinDto.getPhoneNumber())
                .build();
        memberRepository.save(dbJoinMember);
    }
}
