package com.ll.auth.global.baseInit;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.service.MemberService;
import com.ll.auth.domain.post.post.entity.Post;
import com.ll.auth.domain.post.post.service.PostService;
import com.ll.auth.global.app.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final MemberService memberService;
    private final PostService postService;

    @Autowired
    @Lazy
    private BaseInitData self;
    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    //사전에 샘플 데이터 3개 넣어놓기
    @Transactional
    public void work1() {

        if(memberService.count() >0) return;

        Member memberSystem = memberService.join("system", "1234system", "시스템");
        if(AppConfig.isNotProd()) memberSystem.setApikey("system");

        Member memberAdmin = memberService.join("admin", "1234admin", "관리자");
        if(AppConfig.isNotProd()) memberAdmin.setApikey("admin");

        Member memberUser1 = memberService.join("user1", "1234user1", "유저1");
        if(AppConfig.isNotProd()) memberUser1.setApikey("user1");

        Member memberUser2 = memberService.join("user2", "1234user2", "유저2");
        if(AppConfig.isNotProd()) memberUser2.setApikey("user2");

        Member memberUser3 = memberService.join("user3", "1234user3", "유저3");
        if(AppConfig.isNotProd()) memberUser3.setApikey("user3");

        Member memberUser4 = memberService.join("user4", "1234user4", "유저4");
        if(AppConfig.isNotProd()) memberUser4.setApikey("user4");

        Member memberUser5 = memberService.join("user5", "1234user5", "유저5");
        if(AppConfig.isNotProd()) memberUser5.setApikey("user5");
    }

    @Transactional
    public void work2() {

        if(postService.count() >0) return;

        Member memberUser1 = memberService.findByUsername("user1").get();
        Member memberUser2 = memberService.findByUsername("user2").get();
        Member memberUser3 = memberService.findByUsername("user3").get();
        Member memberUser4 = memberService.findByUsername("user4").get();
        Member memberUser5 = memberService.findByUsername("user5").get();

        Post post1 = postService.write(memberUser1,"축구 하실 분?", "14시까지 22명 모집합니다.");
        Post post2 = postService.write(memberUser1,"농구 하실 분?", "15시까지 12명 모집합니다.");
        Post post3 = postService.write(memberUser2,"야구 하실 분?", "16시까지 6명 모집합니다.");
        Post post4 = postService.write(memberUser3,"발야구 하실 분?", "16시까지 6명 모집합니다.");
        Post post5 = postService.write(memberUser4,"수영 하실 분?", "16시까지 6명 모집합니다.");
        Post post6 = postService.write(memberUser5,"등산 하실 분?", "16시까지 6명 모집합니다.");
    }
}