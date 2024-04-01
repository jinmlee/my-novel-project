package com.jinmlee.novel.entity;

import com.jinmlee.novel.enums.MyRole;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
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
