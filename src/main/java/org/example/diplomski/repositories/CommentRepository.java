package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByPostId(Long postId);
    Optional<Comment> findByUserId(Long userId);
    Optional<Comment> findById(Long id);
    Comment save(Comment comment);

}
