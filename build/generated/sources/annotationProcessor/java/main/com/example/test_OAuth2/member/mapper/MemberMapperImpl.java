package com.example.test_OAuth2.member.mapper;

import com.example.test_OAuth2.member.dto.MemberPatchDto;
import com.example.test_OAuth2.member.dto.MemberPostDto;
import com.example.test_OAuth2.member.dto.MemberResponseDto;
import com.example.test_OAuth2.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-02T04:16:08+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberPostToMember(MemberPostDto requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.name( requestBody.getName() );
        member.email( requestBody.getEmail() );
        member.password( requestBody.getPassword() );
        member.imgUrl( requestBody.getImgUrl() );

        return member.build();
    }

    @Override
    public Member memberPatchToMember(MemberPatchDto requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.memberId( requestBody.getMemberId() );
        member.name( requestBody.getName() );
        member.email( requestBody.getEmail() );
        member.password( requestBody.getPassword() );
        member.imgUrl( requestBody.getImgUrl() );
        member.memberStatus( requestBody.getMemberStatus() );

        return member.build();
    }

    @Override
    public MemberResponseDto memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberResponseDto.MemberResponseDtoBuilder memberResponseDto = MemberResponseDto.builder();

        if ( member.getMemberId() != null ) {
            memberResponseDto.memberId( member.getMemberId() );
        }
        memberResponseDto.name( member.getName() );
        memberResponseDto.email( member.getEmail() );
        memberResponseDto.imgUrl( member.getImgUrl() );
        memberResponseDto.memberStatus( member.getMemberStatus() );
        memberResponseDto.createdAt( member.getCreatedAt() );
        memberResponseDto.modifiedAt( member.getModifiedAt() );

        return memberResponseDto.build();
    }

    @Override
    public List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members) {
        if ( members == null ) {
            return null;
        }

        List<MemberResponseDto> list = new ArrayList<MemberResponseDto>( members.size() );
        for ( Member member : members ) {
            list.add( memberToMemberResponseDto( member ) );
        }

        return list;
    }
}
