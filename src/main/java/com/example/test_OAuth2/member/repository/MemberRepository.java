package com.example.test_OAuth2.member.repository;

import com.example.test_OAuth2.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(long memberId);
    Optional<Member> findByEmail(String email);
}
