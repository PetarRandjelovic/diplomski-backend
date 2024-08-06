package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Like;
import org.example.diplomski.data.entites.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

 //   Media findByPostIdAndUserId(Long postId, Long userId);
//    void delete(Long id);

}
