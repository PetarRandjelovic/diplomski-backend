package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.UserRelationshipDto;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserRelationship;
import org.example.diplomski.exceptions.UserEmailNotFoundException;
import org.example.diplomski.mapper.UserRelationshipMapper;
import org.example.diplomski.repositories.UserRelationshipRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.UserRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserRelationshipServiceImpl implements UserRelationshipService {

    private final UserRepository userRepository;
    private final UserRelationshipRepository userRelationshipRepository;
    private final UserRelationshipMapper userRelationshipMapper;

    @Autowired
    public UserRelationshipServiceImpl(UserRepository userRepository, UserRelationshipRepository userRelationshipRepository, UserRelationshipMapper userRelationshipMapper) {
        this.userRepository = userRepository;
        this.userRelationshipRepository = userRelationshipRepository;
        this.userRelationshipMapper = userRelationshipMapper;
    }

    @Override
    public Boolean followUnfollowUser(String senderEmail, String receiverEmail) {
        User sender = userRepository.findByEmail(senderEmail).orElseThrow(() -> new UserEmailNotFoundException(senderEmail));
        User receiver = userRepository.findByEmail(receiverEmail).orElseThrow(() -> new UserEmailNotFoundException(receiverEmail));
        ;


        Optional<UserRelationship> userRelationships1 = userRelationshipRepository.findByUser1AndUser2(sender, receiver);

        if (!userRelationships1.isPresent()) {
            UserRelationship userRelationship = new UserRelationship();
            userRelationship.setUser1(sender);
            userRelationship.setUser2(receiver);
            userRelationship.setConfirmed(true);
            userRelationshipRepository.save(userRelationship);
        } else {
            userRelationshipRepository.delete(userRelationshipRepository.findByUser1AndUser2(sender,receiver).get());

        }

        return true;
    }





@Override
public Boolean unfollowUser(String senderEmail, String receiverEmail) {
    return null;
}

@Override
public List<UserRelationshipDto> getFollowedUsers(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email));
    List<UserRelationship> userRelationships = userRelationshipRepository.findByUser1(user);

    return userRelationships.stream().map(userRelationshipMapper::toDto).toList();
}

@Override
public List<UserRelationshipDto> getFollowingUsers(String email) {

    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email));
    List<UserRelationship> userRelationships = userRelationshipRepository.findByUser2(user);

    return userRelationships.stream().map(userRelationshipMapper::toDto).toList();
}

@Override
public int getFollowerCount(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email));
    return userRelationshipRepository.countFollowers(user.getId());
}

@Override
public int getFollowingCount(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email));
    return userRelationshipRepository.countFollowing(user.getId());
}
}
