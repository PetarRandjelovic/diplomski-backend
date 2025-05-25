package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserRelationship;
import org.example.diplomski.data.enums.RelationshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRelationshipRepository extends JpaRepository<UserRelationship, Long> {
    List<UserRelationship> findByUser1(User user);
    List<UserRelationship> findByUser2(User user2);
    Optional<UserRelationship> findByUser1AndUser2(User user, User followedUser);
    @Query("SELECT COUNT(r) FROM UserRelationship r WHERE r.user1.id = :userId")
    int countFollowing(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM UserRelationship r WHERE r.user2.id = :userId")
    int countFollowers(@Param("userId") Long userId);

   Boolean existsByUser1AndUser2(User user1, User user2);

    @Query("""
        FROM UserRelationship r
        WHERE r.status = :status
          AND (r.user1.id = :userId OR r.user2.id = :userId)
        """)
    List<UserRelationship> findConfirmedByUserAndStatus(@Param("userId") Long userId,@Param("status") RelationshipStatus status);


}