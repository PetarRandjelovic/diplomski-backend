package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.CreateUserRelationshipRecord;
import org.example.diplomski.data.dto.UserRelationship.UserRelationshipAnswerRecord;
import org.example.diplomski.data.dto.UserRelationship.UserRelationshipDto;
import org.example.diplomski.data.dto.UserRelationship.UserRelationshipRecord;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserProfile;
import org.example.diplomski.data.entites.UserRelationship;
import org.example.diplomski.data.enums.RelationshipStatus;
import org.example.diplomski.exceptions.RelationshipExistsException;
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
import java.util.Set;
import java.util.stream.Collectors;

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


        Optional<UserRelationship> userRelationships1 = userRelationshipRepository.findByUser1AndUser2(sender, receiver);

        if (!userRelationships1.isPresent()) {
            UserRelationship userRelationship = new UserRelationship();
            userRelationship.setUser1(sender);
            userRelationship.setUser2(receiver);
            // userRelationship.setConfirmed(true);
            userRelationshipRepository.save(userRelationship);
        } else {
            userRelationshipRepository.delete(userRelationshipRepository.findByUser1AndUser2(sender, receiver).get());

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

    @Override
    public UserRelationshipDto createFriendRequest(CreateUserRelationshipRecord createUserRelationshipRecord) {
        String receiverEmail = createUserRelationshipRecord.emailReceiver();
        String senderEmail = createUserRelationshipRecord.emailSender();

        User userSender = userRepository.findByEmail(senderEmail).orElseThrow(() -> new UserEmailNotFoundException(receiverEmail));
        User userReciever = userRepository.findByEmail(receiverEmail).orElseThrow(() -> new UserEmailNotFoundException(receiverEmail));


        boolean exist = userRelationshipRepository.existsByUser1AndUser2(userReciever, userSender);

        if (exist)
            throw new RelationshipExistsException(senderEmail, receiverEmail);

        System.out.println(userSender + " " + userReciever);
        UserRelationship relationship = new UserRelationship();
        relationship.setUser1(userSender);
        relationship.setUser2(userReciever);
        relationship.setStatus(RelationshipStatus.WAITING);


        return userRelationshipMapper.toDto(userRelationshipRepository.save(relationship));
    }

    @Override
    public UserRelationshipDto userRelationshipAnswer(UserRelationshipAnswerRecord userRelationshipAnswerRecord) {
        User user1 = userRepository.findByEmail(userRelationshipAnswerRecord.emailSender()).orElseThrow(() -> new UserEmailNotFoundException(userRelationshipAnswerRecord.emailSender()));
        User user2 = userRepository.findByEmail(userRelationshipAnswerRecord.emailReceiver()).orElseThrow(() -> new UserEmailNotFoundException(userRelationshipAnswerRecord.emailReceiver()));
        Optional<UserRelationship> userRelationship = userRelationshipRepository.findByUser1AndUser2(user1, user2);

        if (userRelationshipAnswerRecord.status()) {
            userRelationship.ifPresent(relationship -> relationship.setStatus(RelationshipStatus.CONFIRMED));
            userRelationshipRepository.save(userRelationship.get());
            return userRelationshipMapper.toDto(userRelationship.get());
        } else {
            userRelationship.ifPresent(relationship -> relationship.setStatus(RelationshipStatus.DECLINED));
            userRelationshipRepository.delete(userRelationshipRepository.findByUser1AndUser2(user1, user2).get());
        }

        return null;
    }

    @Override
    public int getUserFriendsCount(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email));
        List<UserRelationship> list = userRelationshipRepository.findConfirmedByUserAndStatus(user.getId(), RelationshipStatus.CONFIRMED);
        return list.size();
    }

    @Override
    public UserRelationshipRecord getRelationshipStatus(String email1, String email2) {
        User user1 = userRepository.findByEmail(email1)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User user2 = userRepository.findByEmail(email2)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserRelationship relationship = userRelationshipRepository
                .findByUser1AndUser2OrUser2AndUser1(user1, user2, user1, user2)
                .orElse(null);

        RelationshipStatus relationshipStatus = relationship != null ? relationship.getStatus() : RelationshipStatus.NEUTRAL;

        return userRelationshipMapper.toUserRelationRecord(email1,email2,relationshipStatus);

    }


    @Override
    public List<UserRelationshipRecord> getIncomingRequests(String email) {
        List<UserRelationship> relationships = userRelationshipRepository
                .findByUser2EmailAndStatus(email, RelationshipStatus.WAITING);

        return relationships.stream()
                .map(rel -> new UserRelationshipRecord(
                        rel.getUser1().getEmail(),
                        rel.getUser2().getEmail(),
                        rel.getStatus()
                )).toList();
    }

}