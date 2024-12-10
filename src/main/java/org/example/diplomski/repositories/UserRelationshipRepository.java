package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRelationshipRepository extends JpaRepository<UserRelationship, Long> {
    List<UserRelationship> findByUser(User user);
    List<UserRelationship> findByFollowedUser(User follower);
    Optional<UserRelationship> findByUserAndFollowedUser(User user, User followedUser);

}