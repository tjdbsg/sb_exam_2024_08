package com.ll.sapp.domain.post.controller;

import com.ll.sapp.domain.post.service.PostService;
import com.ll.sapp.global.rq.Rq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final Rq rq;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("posts", postService.findAllByOrderByIdDesc());

        return "post/list";
    }


    @AllArgsConstructor
    public static class PostWriteForm {
        @NotBlank
        private String title;
        @NotBlank
        private String body;
    }

    @GetMapping("/write")
    public String showWrite() {
        return "post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@Valid PostWriteForm form) {
        postService.write(rq.getMember(), form.title, form.body);

        return "redirect:/post/list";
    }
}