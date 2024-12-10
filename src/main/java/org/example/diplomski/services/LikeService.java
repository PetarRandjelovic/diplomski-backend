package org.example.diplomski.services;

import org.example.diplomski.data.dto.LikeDto;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {

    LikeDto likePost(LikeDto likeDto);
    LikeDto likeComment(LikeDto likeDto);
    boolean isPostLikedByUser(Long postId, Long userId);
    boolean isCommentLikedByUser(Long commentId, Long userId);
    LikeDto findByPostId(Long postId);
    LikeDto findByCommentId(Long commentId);
    LikeDto findByUserId(Long userId);
    LikeDto findById(Long id);
    LikeDto update(LikeDto likeDto);
    Boolean delete(Long id);
}
