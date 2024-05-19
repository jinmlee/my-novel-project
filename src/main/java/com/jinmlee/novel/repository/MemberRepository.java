package com.jinmlee.novel.repository;

import com.jinmlee.novel.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUserId(String userId);

    Optional<Member> findByUserId(String userId);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.password = :password WHERE m.id = :id")
    void updatePassword(@Param("id") Long id, @Param("password") String password);
}
