package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Like;
import org.example.diplomski.data.entites.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    boolean existsByPostIdAndUserId(Long postId, Long userId);
    LikePost findByPostIdAndUserId(Long postId, Long userId);
    List<LikePost> findByPostId(Long postId);
}
