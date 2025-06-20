package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostId(Long postId);
    void deleteByUserId(Long userId);
    Like findByCommentIdAndUserId(Long commentId, Long userId);
    List<Like> findByPostId(Long postId);
    List<Like> findByCommentId(Long commentId);



}
