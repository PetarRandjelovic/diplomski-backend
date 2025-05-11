package org.example.diplomski.services;

import org.example.diplomski.data.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {


    CommentDto findById(Long id);
    CommentDto update(CommentDto commentDto);
    Boolean delete(Long id);
    CommentDto save(CommentDto commentDto);
    List<CommentDto> findCommentsByPostId(Long postId);
    CommentDto findByUserId(Long userId);
    CommentDto createPostByPostId(Long postId, String username,String comment);



}
