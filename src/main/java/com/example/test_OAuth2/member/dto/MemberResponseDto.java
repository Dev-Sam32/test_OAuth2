package com.example.test_OAuth2.member.dto;

import com.example.test_OAuth2.member.entity.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private long memberId;
    private String name;
    private String email;
//    private String password;
    private String imgUrl;
    private MemberStatus memberStatus;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

//    private List<LuckMangoResponseDto> luckMangos;
//    private List<ReviewResponseDto> reviews;
}
