package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);
    Optional<Comment> findByUserId(Long userId);
    Optional<Comment> findById(Long id);
    Comment save(Comment comment);

}
