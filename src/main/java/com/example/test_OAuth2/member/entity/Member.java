package com.example.test_OAuth2.member.entity;

import com.example.test_OAuth2.audit.Auditable;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MEMBERS")
public class Member extends Auditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    @Length(max=10)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column             // imgUrl 얘만 기본값 Null로 생성됨
    private String imgUrl;

    @ElementCollection(fetch = FetchType.EAGER) // 사용자 등록 시, 사용자의 권한을 등록하기 위한 권한 테이블을 생성
    @Column(nullable = false)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

//    // 멤버 -> 복망고, 멤버 -> 리뷰는 1:N 관계
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<LuckMango> luckMangos = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Review> reviews = new ArrayList<>();

//    public void addLuckMango(LuckMango luckMango) {
//        luckMangos.add(luckMango);
//        if(luckMango.getMember() != this){
//            luckMango.setMember(this);
//        }
//    }
//
//    public void addReview(Review review) {
//        reviews.add(review);
//        if(review.getMember() != this) {
//            review.setMember(this);
//        }
//    }
    public Member(String email) {
        this.email = email;
    }

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}