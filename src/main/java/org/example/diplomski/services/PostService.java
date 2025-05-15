package org.example.diplomski.services;

import org.example.diplomski.data.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto findById(Long id);
    PostDto update(PostDto postDto);
    Boolean delete(Long id);
    PostDto save(PostDto postDto);
    PostDto createPost(PostDto postDto);
    List<PostDto> findAll();


    List<PostDto> findByEmail(String email);
}
