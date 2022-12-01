package com.example.test_OAuth2.helper.event;

import com.example.test_OAuth2.member.entity.Member;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberRegistrationApplicationEvent extends ApplicationEvent {
    private Member member;
    public MemberRegistrationApplicationEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }
}
