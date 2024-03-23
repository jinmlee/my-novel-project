package com.jinmlee.novel;

import com.jinmlee.novel.entity.Member;
import com.jinmlee.novel.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NovelApplicationTests {

	@Autowired
	MemberRepository memberRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void test(){


//		Member member = memberRepository.findByUserId("a");
//
//		System.out.println(member.getUserId());
	}

}
