package org.example.diplomski.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.entites.*;
import org.example.diplomski.data.enums.RelationshipStatus;
import org.example.diplomski.data.enums.RoleType;
import org.example.diplomski.exceptions.UserEmailNotFoundException;
import org.example.diplomski.jwtUtils.ImageUtils;
import org.example.diplomski.repositories.*;
import org.example.diplomski.services.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapData.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRelationshipRepository userRelationshipRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final UserProfileRepository userProfileRepository;
    private final ImageDataRepository imageDataRepository;
    private final UserLoaderService userLoaderService;

    public void run(String... args) {
        try {
            logger.info("USERService: DEV DATA LOADING IN PROGRESS...");


            loadTags();
            loadRoles();
          //  loadImages();
        //    loadUsers();
            userLoaderService.loadUsers();
        //    loadProfiles();
            loadUserRelationships();
            loadPosts();
            loadComments();


            logger.info("USERService: DEV DATA LOADING FINISHED...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImages() {
        if (imageDataRepository.count() == 0) {
            try (InputStream in =
                         getClass().getResourceAsStream("/avatars/avatar.png")) {

                if (in == null) {
                    throw new IllegalStateException(
                            "avatars/avatar.png not on class-path");
                }

                ImageData img = ImageData.builder()
                        .name("avatar.png")
                        .type("image/png")
                        .imageData(ImageUtils.compressImage(in.readAllBytes()))
                        .build();

                imageDataRepository.save(img);


            } catch (IOException e) {
                throw new IllegalStateException("Cannot read default avatar", e);
            }
        }
    }


    private void loadTags() {
        if (tagRepository.count() == 0) {
            Tag tag1 = new Tag();
            tag1.setName("Travel");
            tagRepository.save(tag1);

            Tag tag2 = new Tag();
            tag2.setName("Food");
            tagRepository.save(tag2);
        }
    }

    private void loadComments() {
        if (commentRepository.count() == 0) {
            Comment comment1 = new Comment();
            comment1.setUser(userRepository.findByEmail("marko@gmail.com").get());
            comment1.setContent("This is a comment from Marko");
            comment1.setPost(postRepository.findById(1L).get());
            commentRepository.save(comment1);
        }
    }

    private void loadPosts() {
        if (postRepository.count() == 0) {
            Post post1 = new Post();
            post1.setUser(userRepository.findByEmail("mirko@gmail.com").get());
            post1.setContent("This is a post from Mirko");
            post1.setTags(tagRepository.findAll());
            postRepository.save(post1);

            Post post2 = new Post();
            post2.setUser(userRepository.findByEmail("mirko@gmail.com").get());
            post2.setContent("This is a post from Mirko as well");
            post2.setTags(tagRepository.findAll());
            postRepository.save(post2);
        }
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            Role role1 = new Role();
            role1.setRoleType(RoleType.ADMIN);
            roleRepository.save(role1);

            Role role2 = new Role();
            role2.setRoleType(RoleType.USER);
            roleRepository.save(role2);

            Role role3 = new Role();
            role3.setRoleType(RoleType.PRIVATE);
            roleRepository.save(role3);

            Role role4 = new Role();
            role4.setRoleType(RoleType.PUBLIC);
            roleRepository.save(role4);
        }

    }

    @Transactional
    public void loadUsers() {
        if (userRepository.count() == 0) {

            Role privateRole = roleRepository.findByRoleType(RoleType.PRIVATE).get();
            Role adminRole = roleRepository.findByRoleType(RoleType.ADMIN).get();

            //   ImageData img=imageDataRepository.findByName("avatar.png").get();
            //   Optional<ImageData> dbImageData = imageDataRepository.findByName("avatar.png");
            Optional<ImageData> optionalImg = imageDataRepository.findByName("avatar.png");

            ImageData profileImage;
            if (optionalImg.isPresent()) {
                profileImage = optionalImg.get();
            } else {
                try {
                    profileImage = ImageData.builder()
                            .name("avatar.png")
                            .type("image/png")
                            .imageData(Files.readAllBytes(Paths.get("avatar/avatar.png")))
                            .build();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                profileImage = imageDataRepository.save(profileImage);
            }

            User user2 = new User();
            user2.setEmail("marko@gmail.com");
            user2.setUsername("marko@gmail.com");
            user2.setPassword(passwordEncoder.encode("marko"));
            user2.setRole(privateRole);
          //  userRepository.save(user2);

            UserProfile profile2 = new UserProfile();
            //  profile.setProfilePictureUrl(img);
            profile2.setCity("Belgrade");
            profile2.setInterests(new ArrayList<>());
            profile2.setUser(user2);
          //  profile2.setProfilePictureUrl(profileImage);

            userProfileRepository.save(profile2);

            //       createUserProfile(user2);


            User user3 = new User();
            user3.setEmail("mirko@gmail.com");
            user3.setUsername("mirko@gmail.com");
            user3.setPassword(passwordEncoder.encode("mirko"));
            user3.setRole(privateRole);
           // userRepository.save(user3);

            UserProfile profile1 = new UserProfile();
            //  profile.setProfilePictureUrl(img);
            profile1.setCity("Belgrade");
            profile1.setInterests(new ArrayList<>());
            profile1.setUser(user3);

            userProfileRepository.save(profile1);
            //    createUserProfile(user3);

            User user1 = new User();
            user1.setEmail("petar@gmail.com");
            user1.setUsername("petar@gmail.com");
            user1.setPassword(passwordEncoder.encode("petar"));
            user1.setRole(adminRole);
            //   userRepository.save(user1);

            UserProfile profile = new UserProfile();
            //  profile.setProfilePictureUrl(img);
            profile.setCity("Belgrade");
            profile.setInterests(new ArrayList<>());
            profile.setUser(user1);

            userProfileRepository.save(profile);


            String[][] users = {
                    {"ana.jovic@gmail.com", "ana_jovic", "ana123"},
                    {"nikola.petrovic@gmail.com", "nikola_petrovic", "nikola123"},
                    {"jelena.m@gmail.com", "jelena_m", "jelena123"},
                    {"uros.ivanovic@gmail.com", "uros_ivanovic", "uros123"},
                    {"tijana.k@gmail.com", "tijana_k", "tijana123"},
                    {"stefan.nikolic@gmail.com", "stefan_n", "stefan123"},
                    {"milica.s@gmail.com", "milica_s", "milica123"},
                    {"filip.djordjevic@gmail.com", "filip_d", "filip123"},
                    {"katarina.l@gmail.com", "katarina_l", "katarina123"},
                    {"dusan.radovic@gmail.com", "dusan_r", "dusan123"}
            };

            for (String[] userData : users) {
                User user = new User();
                user.setEmail(userData[0]);
                user.setUsername(userData[1]);
                user.setPassword(passwordEncoder.encode(userData[2]));
                user.setRole(privateRole);
              //  userRepository.save(user);

                UserProfile profile3 = new UserProfile();
                //  profile.setProfilePictureUrl(img);
                profile3.setCity("Belgrade");
                profile3.setInterests(new ArrayList<>());
                profile3.setUser(user);

                userProfileRepository.save(profile3);
                //     createUserProfile(user);
            }
        }
    }


//    protected void createUserProfile(User user) {
//        if (userProfileRepository.findByUserEmail(user.getEmail()).isEmpty()) {
//            UserProfile profile = new UserProfile();
//            profile.setUser(user);
//
//            Optional<ImageData> imageDataOpt = imageDataRepository.findByNameWithLob("avatar.png");
//            imageDataOpt.ifPresent(img -> {
//                img.getImageData(); // Forces LOB initialization inside the session
//                profile.setProfilePictureUrl(img);
//            });
//
//            userProfileRepository.save(profile);
//        }
//    }


    private void loadUserRelationships() {

        if (userRelationshipRepository.count() == 0) {
            UserRelationship userRelationship1 = new UserRelationship();
            userRelationship1.setUser1(userRepository.findByEmail("petar@gmail.com").get());
            userRelationship1.setUser2(userRepository.findByEmail("marko@gmail.com").get());
            userRelationship1.setStatus(RelationshipStatus.CONFIRMED);
            userRelationshipRepository.save(userRelationship1);

            UserRelationship userRelationship2 = new UserRelationship();
            userRelationship2.setUser1(userRepository.findByEmail("petar@gmail.com").get());
            userRelationship2.setUser2(userRepository.findByEmail("mirko@gmail.com").get());
            userRelationship2.setStatus(RelationshipStatus.CONFIRMED);
            userRelationshipRepository.save(userRelationship2);

            UserRelationship userRelationship3 = new UserRelationship();
            userRelationship3.setUser1(userRepository.findByEmail("marko@gmail.com").get());
            userRelationship3.setUser2(userRepository.findByEmail("mirko@gmail.com").get());
            userRelationship3.setStatus(RelationshipStatus.CONFIRMED);
            userRelationshipRepository.save(userRelationship3);


//            UserRelationship userRelationship4 = new UserRelationship();
            //            userRelationship4.setUser1(userRepository.findByEmail("mirko@gmail.com").get());
//            userRelationship4.setUser2(userRepository.findByEmail("marko@gmail.com").get());
//            userRelationshipRepository.save(userRelationship4);


        }
    }
}
