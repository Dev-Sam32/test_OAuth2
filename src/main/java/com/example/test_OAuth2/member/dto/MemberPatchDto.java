package com.example.test_OAuth2.member.dto;

import com.example.test_OAuth2.member.entity.MemberStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberPatchDto {
    private long memberId;
    private String name;
    private String email;
    private String password;
    private String imgUrl;
    private long tot_Money;
    private MemberStatus memberStatus;
    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
}
