package org.example.diplomski.services;

import org.example.diplomski.data.dto.PostDto;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    PostDto findById(Long id);
    PostDto update(PostDto postDto);
    Boolean delete(Long id);
    PostDto save(PostDto postDto);

}
