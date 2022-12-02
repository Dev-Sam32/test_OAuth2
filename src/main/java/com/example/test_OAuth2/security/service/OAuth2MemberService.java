package com.example.test_OAuth2.security.service;

import com.example.test_OAuth2.exception.BusinessLogicException;
import com.example.test_OAuth2.exception.ExceptionCode;
import com.example.test_OAuth2.member.entity.Member;
import com.example.test_OAuth2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class OAuth2MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        member.setRoles(member.getRoles());

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}