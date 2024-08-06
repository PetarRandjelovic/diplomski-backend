package org.example.diplomski.services;

import org.example.diplomski.data.dto.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {


    CommentDto findById(Long id);
    CommentDto update(CommentDto commentDto);
    Boolean delete(Long id);
    CommentDto save(CommentDto commentDto);
    CommentDto findByPostId(Long postId);
    CommentDto findByUserId(Long userId);



}
