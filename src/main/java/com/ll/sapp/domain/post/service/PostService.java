package com.ll.sapp.domain.post.service;

import com.ll.sapp.domain.member.entity.Member;
import com.ll.sapp.domain.post.entity.Post;
import com.ll.sapp.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post write(Member author, String title, String body) {
        Post post = Post.builder()
                .author(author)
                .title(title)
                .body(body)
                .build();

        return postRepository.save(post);
    }

    public List<Post> findAllByOrderByIdDesc() {
        return postRepository.findAllByOrderByIdDesc();
    }
}