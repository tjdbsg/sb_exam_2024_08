package com.ll.sapp.global.initData;

import com.ll.sapp.domain.member.MemberService;
import com.ll.sapp.domain.member.entity.Member;
import com.ll.sapp.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Lazy
    @Autowired
    private NotProd self;

    private final MemberService memberService;
    private final PostService postService;

    @Bean
    public ApplicationRunner initNotProd() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.findByUsername("system").isPresent()) return;

        Member memberSystem = memberService.join("system", "1234").getData();
        Member memberAdmin = memberService.join("admin", "1234").getData();
        Member memberUser1 = memberService.join("user1", "1234").getData();
        Member memberUser2 = memberService.join("user2", "1234").getData();
        Member memberUser3 = memberService.join("user3", "1234").getData();

        postService.write(memberUser1, "제목 1", "내용 1");
        postService.write(memberUser1, "제목 2", "내용 2");
        postService.write(memberUser1, "제목 3", "내용 3");
        postService.write(memberUser1, "제목 4", "내용 4");

        postService.write(memberUser2, "제목 5", "내용 5");
        postService.write(memberUser2, "제목 6", "내용 6");
    }
}