package com.jinmlee.novel.entity;

import com.jinmlee.novel.enums.MyRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userId;
    private String userName;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private MyRole role;
}
