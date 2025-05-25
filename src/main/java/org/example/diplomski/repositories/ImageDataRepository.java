package org.example.diplomski.repositories;


import org.example.diplomski.data.entites.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);

    @Query("SELECT i FROM ImageData i WHERE i.name = :name")
    Optional<ImageData> findByNameWithLob(@Param("name") String name);
}