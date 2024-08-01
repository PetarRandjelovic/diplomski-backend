package org.example.diplomski.bootstrap;

import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.entites.Role;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserRelationship;
import org.example.diplomski.data.enums.RoleType;
import org.example.diplomski.repositories.RoleRepository;
import org.example.diplomski.repositories.UserRelationshipRepository;
import org.example.diplomski.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapData.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRelationshipRepository userRelationshipRepository;

    public void run(String... args) {
        try {
            logger.info("USERService: DEV DATA LOADING IN PROGRESS...");

            loadRoles();
            loadUsers();
            loadUserRelationships();


            logger.info("USERService: DEV DATA LOADING FINISHED...");
        } catch (Exception e) {
            e.printStackTrace();
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
        }

    }

    private void loadUsers() {


        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setEmail("petar@gmail.com");
            user1.setUsername("petar@gmail.com");
            user1.setPassword(passwordEncoder.encode("petar"));
            user1.setRole(roleRepository.findByRoleType(RoleType.USER).get());
            userRepository.save(user1);

            User user2 = new User();
            user2.setEmail("marko@gmail.com");
            user2.setUsername("marko@gmail.com");
            user2.setPassword(passwordEncoder.encode("marko"));
            user2.setRole(roleRepository.findByRoleType(RoleType.USER).get());
            userRepository.save(user2);

            User user3 = new User();
            user3.setEmail("mirko@gmail.com");
            user3.setUsername("mirko@gmail.com");
            user3.setPassword(passwordEncoder.encode("mirko"));
            user3.setRole(roleRepository.findByRoleType(RoleType.USER).get());
            userRepository.save(user3);
        }

    }


    private void loadUserRelationships() {

        if (userRelationshipRepository.count() == 0) {
            UserRelationship userRelationship1 = new UserRelationship();
            userRelationship1.setUser(userRepository.findByEmail("petar@gmail.com").get());
            userRelationship1.setFollowedUser(userRepository.findByEmail("marko@gmail.com").get());
            userRelationshipRepository.save(userRelationship1);

            UserRelationship userRelationship2 = new UserRelationship();
            userRelationship2.setUser(userRepository.findByEmail("petar@gmail.com").get());
            userRelationship2.setFollowedUser(userRepository.findByEmail("mirko@gmail.com").get());
            userRelationshipRepository.save(userRelationship2);

            UserRelationship userRelationship3 = new UserRelationship();
            userRelationship3.setUser(userRepository.findByEmail("marko@gmail.com").get());
            userRelationship3.setFollowedUser(userRepository.findByEmail("mirko@gmail.com").get());
            userRelationshipRepository.save(userRelationship3);


        }
    }
}
