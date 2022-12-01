package com.example.test_OAuth2.member.mapper;

import com.example.test_OAuth2.member.dto.MemberPatchDto;
import com.example.test_OAuth2.member.dto.MemberPostDto;
import com.example.test_OAuth2.member.dto.MemberResponseDto;
import com.example.test_OAuth2.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberPostToMember(MemberPostDto requestBody);
    Member memberPatchToMember(MemberPatchDto requestBody);
    MemberResponseDto memberToMemberResponseDto(Member member);
    List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);
}
