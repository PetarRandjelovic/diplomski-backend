package org.example.diplomski.repositories;

import org.example.diplomski.data.dto.UserProfileRecord;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {


    Optional<UserProfile> findByUserEmail(String email);

    Optional<UserProfile> findByUserId(Long id);

    Optional<UserProfile> findByUser(User user);



}
