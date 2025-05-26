package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Integer removeUserByEmail(String email);

    Optional<User> findById(int id);


//    @Query("SELECT u FROM User u WHERE u.id NOT IN :excludedIds")
//    List<User> findAllExcludingIds(@Param("excludedIds") Set<Long> excludedIds);


    @Query("SELECT u FROM User u WHERE u.email NOT IN :emails")
    List<User> findAllExcludingEmails(@Param("emails") Set<String> emails);

}
