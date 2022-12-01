package com.example.test_OAuth2.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto_Mango {
    private long memberId;
    private String name;
    private String email;
    private String imgUrl;
}
