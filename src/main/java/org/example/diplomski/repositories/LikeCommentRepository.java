package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Like;
import org.example.diplomski.data.entites.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    boolean existsByCommentIdAndUserId(Long postId, Long userId);
    List<LikeComment> findByCommentId(Long commentId);
}
