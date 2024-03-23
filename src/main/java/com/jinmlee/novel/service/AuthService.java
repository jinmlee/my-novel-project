package com.jinmlee.novel.service;

import com.jinmlee.novel.dto.JoinDto;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.enums.MyRole;
import com.jinmlee.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinDto joinDto){

        boolean isUser = memberRepository.existsByUserId(joinDto.getUserId());
        if(isUser){
            return;
        }


        Member dbJoinMember = Member.builder()
                .userId(joinDto.getUserId())
                .nickname(joinDto.getNickname())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .userName(joinDto.getUserName())
                .email(joinDto.getEmail())
                .phoneNumber(joinDto.getPhoneNumber())
                .role(MyRole.USER)
                .build();
        memberRepository.save(dbJoinMember);
    }
}
