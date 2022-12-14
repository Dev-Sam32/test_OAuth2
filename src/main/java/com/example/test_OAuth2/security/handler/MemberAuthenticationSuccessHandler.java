package com.example.test_OAuth2.security.handler;

import com.example.test_OAuth2.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    // Authentication 객체에 사용자 정보를 얻은 후, HttpServletResponse로 출력 스트림을 생성하여 response를 전송
    private MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Authentication authentication) throws IOException {
        log.info("# Authenticated successfully!");

//        LoginDto loginDto = (LoginDto) authentication.getPrincipal();
//        System.out.println(loginDto);
//
//        String memberEmail = loginDto.getUsername();
//        log.info(memberEmail);
//        System.out.println(memberEmail);
//
//        Member member = memberService.findMember(memberEmail);
//        long memberId = member.getMemberId();
//
//        log.info(Long.toString(memberId));
//        System.out.println(memberId);
//        response.addHeader("memberId", Long.toString(memberId));
//        response.setIntHeader("memberId", (int)(long)memberId);
    }
}