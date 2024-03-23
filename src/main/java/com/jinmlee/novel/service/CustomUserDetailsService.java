package com.jinmlee.novel.service;


import com.jinmlee.novel.dto.CustomUserDetails;
import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> member = memberRepository.findByUserId(username);

        if(member.isPresent()){
            return new CustomUserDetails(member.get());
        }

        throw new UsernameNotFoundException("아이디와 비밀번호를 확인해 주세요");
    }
}
