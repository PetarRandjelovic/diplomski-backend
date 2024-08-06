package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostIdAndUserId(Long postId, Long userId);
    void deleteByPostId(Long postId);
    void deleteByUserId(Long userId);
  //  void delete(Long id);


}
