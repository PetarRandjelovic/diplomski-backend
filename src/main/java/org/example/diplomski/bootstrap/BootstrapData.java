package org.example.diplomski.bootstrap;

import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.entites.*;
import org.example.diplomski.data.enums.RoleType;
import org.example.diplomski.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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

    public void run(String... args) {
        try {
            logger.info("USERService: DEV DATA LOADING IN PROGRESS...");

            loadTags();
            loadRoles();
            loadUsers();
            loadUserRelationships();
            loadPosts();
            loadComments();


            logger.info("USERService: DEV DATA LOADING FINISHED...");
        } catch (Exception e) {
            e.printStackTrace();
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

    private void loadUsers() {


        if (userRepository.count() == 0) {


            User user2 = new User();
            user2.setEmail("marko@gmail.com");
            user2.setUsername("marko@gmail.com");
            user2.setPassword(passwordEncoder.encode("marko"));
            user2.setRole(roleRepository.findByRoleType(RoleType.PRIVATE).get());
            userRepository.save(user2);

            User user3 = new User();
            user3.setEmail("mirko@gmail.com");
            user3.setUsername("mirko@gmail.com");
            user3.setPassword(passwordEncoder.encode("mirko"));
            user3.setRole(roleRepository.findByRoleType(RoleType.PRIVATE).get());
            userRepository.save(user3);

            User user1 = new User();
            user1.setEmail("petar@gmail.com");
            user1.setUsername("petar@gmail.com");
            user1.setPassword(passwordEncoder.encode("petar"));
            user1.setRole(roleRepository.findByRoleType(RoleType.ADMIN).get());
            userRepository.save(user1);

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

            Role privateRole = roleRepository.findByRoleType(RoleType.PRIVATE).get();

            for (String[] userData : users) {
                User user = new User();
                user.setEmail(userData[0]);
                user.setUsername(userData[1]);
                user.setPassword("password");
                user.setRole(privateRole);
                userRepository.save(user);
            }
        }

    }


    private void loadUserRelationships() {

        if (userRelationshipRepository.count() == 0) {
            UserRelationship userRelationship1 = new UserRelationship();
            userRelationship1.setUser1(userRepository.findByEmail("petar@gmail.com").get());
            userRelationship1.setUser2(userRepository.findByEmail("marko@gmail.com").get());
            userRelationshipRepository.save(userRelationship1);

            UserRelationship userRelationship2 = new UserRelationship();
            userRelationship2.setUser1(userRepository.findByEmail("petar@gmail.com").get());
            userRelationship2.setUser2(userRepository.findByEmail("mirko@gmail.com").get());
            userRelationshipRepository.save(userRelationship2);

            UserRelationship userRelationship3 = new UserRelationship();
            userRelationship3.setUser1(userRepository.findByEmail("marko@gmail.com").get());
            userRelationship3.setUser2(userRepository.findByEmail("mirko@gmail.com").get());
            userRelationshipRepository.save(userRelationship3);


        }
    }
}
