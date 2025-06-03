package org.example.diplomski.services;

import org.example.diplomski.data.dto.post.PostDto;
import org.example.diplomski.data.dto.post.PostRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostRecord findById(Long id);
    PostDto update(PostDto postDto);
    Boolean delete(Long id);
    PostDto save(PostDto postDto);
    PostDto createPost(PostDto postDto);
    List<PostRecord> findAll();


    List<PostRecord> findByEmail(String email);

    List<PostRecord> findByTag(List<String> tag);


    List<PostRecord> findByUserId(Long id);
}
