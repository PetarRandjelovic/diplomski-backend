package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    Optional<Post> findByUserId(Long userId);
    Optional<Post> findByUserEmail(String email);
    List<Post> findAllByUserId(Long userId);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.tags t WHERE t.name IN :tagNames")
    List<Post> findByTags(@Param("tagNames") List<String> tagNames);
}
