package org.example.diplomski.repositories;

import org.example.diplomski.data.entites.Media;
import org.example.diplomski.data.entites.Message;
import org.example.diplomski.data.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Long> {
    List<Message> findByReceiver(User receiver);
    List<Message> findBySenderAndReceiver(User sender, User receiver);

}
