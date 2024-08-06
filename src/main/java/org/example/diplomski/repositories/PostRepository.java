package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    Optional<Post> findByUserId(Long userId);
   // Optional<Post> findById(Long id);
  //  Post save(Post post);
  //  void delete(Long id);

}
