package com.study.springboot.service;

import com.study.springboot.domain.posts.Posts;
import com.study.springboot.domain.posts.PostsRepository;
import com.study.springboot.web.dto.PostsListResponseDto;
import com.study.springboot.web.dto.PostsResponseDto;
import com.study.springboot.web.dto.PostsSaveRequestDto;
import com.study.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) {

        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );

        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {

        return postsRepository.findAllDesc().stream()
                            .map(PostsListResponseDto::new)
                            .collect(Collectors.toList());
    }

    @Transactional
    public Long delete(Long id) {

        postsRepository.delete(
            postsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
            )
        );

        return id;
    }
}
