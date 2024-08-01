package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.UserRelationshipDto;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserRelationship;
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
        Optional<User> sender = userRepository.findByEmail(senderEmail);
        Optional<User> receiver = userRepository.findByEmail(receiverEmail);

        if (sender.isPresent() && receiver.isPresent()) {

            Optional<UserRelationship> userRelationships1 = userRelationshipRepository.findByUserAndFollowedUser(sender.get(), receiver.get());

            if (!userRelationships1.isPresent()) {
                UserRelationship userRelationship = new UserRelationship();
                userRelationship.setUser(sender.get());
                userRelationship.setFollowedUser(receiver.get());
                userRelationshipRepository.save(userRelationship);
            } else {
                userRelationshipRepository.delete(userRelationshipRepository.findByUser(sender.get()).get(0));
            }
            return true;
        }
        throw new RuntimeException("User not found.");

    }

    @Override
    public Boolean unfollowUser(String senderEmail, String receiverEmail) {
        return null;
    }

    @Override
    public List<UserRelationshipDto> getFollowedUsers(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with id: " + email + " not found."));
        List<UserRelationship> userRelationships = userRelationshipRepository.findByUser(user);
        List<UserRelationshipDto> userRelationshipDtos = userRelationships.stream().map(userRelationshipMapper::toDto).toList();

        return userRelationshipDtos;
    }

    @Override
    public List<UserRelationshipDto> getFollowers(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with id: " + email + " not found."));
        List<UserRelationship> userRelationships = userRelationshipRepository.findByFollowedUser(user);
        List<UserRelationshipDto> userRelationshipDtos = userRelationships.stream().map(userRelationshipMapper::toDto).toList();

        return userRelationshipDtos;
    }
}
