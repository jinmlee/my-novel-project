package com.jinmlee.novel.repository;

import com.jinmlee.novel.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUserId(String userId);

    Optional<Member> findByUserId(String userId);
}
