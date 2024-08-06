package org.example.diplomski.services;

import org.example.diplomski.data.dto.LikeDto;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {

    void likePost(Long postId, Long userId);
    void unlikePost(Long postId, Long userId);
    void likeComment(Long commentId, Long userId);
    void unlikeComment(Long commentId, Long userId);
    boolean isPostLikedByUser(Long postId, Long userId);
    boolean isCommentLikedByUser(Long commentId, Long userId);
    LikeDto findByPostId(Long postId);
    LikeDto findByCommentId(Long commentId);
    LikeDto findByUserId(Long userId);
    LikeDto findById(Long id);
    LikeDto update(LikeDto likeDto);
    Boolean delete(Long id);
}
