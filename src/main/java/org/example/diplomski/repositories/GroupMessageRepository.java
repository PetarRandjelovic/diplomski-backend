package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.ChatGroup;
import org.example.diplomski.data.entites.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {
    List<GroupMessage> findByGroupId(Long groupId);
}
