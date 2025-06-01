package org.example.diplomski.services;

import org.example.diplomski.data.dto.like.LikeCommentDto;
import org.example.diplomski.data.dto.LikeDto;
import org.example.diplomski.data.dto.like.LikePostDto;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {

    LikePostDto likePost(LikePostDto likePostDto);
    LikeCommentDto likeComment(LikeCommentDto likeCommentDto);
    boolean isPostLikedByUser(Long postId, Long userId);
    boolean isCommentLikedByUser(Long commentId, Long userId);
    LikeDto findByPostId(Long postId);
    LikeDto findByCommentId(Long commentId);
    LikeDto findByUserId(Long userId);
    LikeDto findById(Long id);
    LikeDto update(LikeDto likeDto);
    Boolean delete(Long id);
    int getPostLikesCount(Long postId);
    int getCommentLikesCount(Long commentId);
    boolean hasUserLikedPost(Long postId, String email);
    boolean hasUserLikedComment(Long commentId, String email);
}
