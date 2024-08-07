package com.ll.sapp.domain.member.controller;

import com.ll.sapp.domain.member.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {
        return "member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public String showMe() {
        return "member/me";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }


    @AllArgsConstructor
    public static class MemberJoinForm {
        @NotBlank
        @Size(min = 3)
        private String username;

        @NotBlank
        @Size(min = 4)
        private String password;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        @Size(min = 2)
        private String nickname;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid MemberJoinForm form) {
        if (memberService.findByUsername(form.username).isPresent())
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");

        memberService.join(form.username, form.password, form.email, form.nickname, "");

        return "redirect:/member/login";
    }
}
