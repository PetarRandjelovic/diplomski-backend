package org.example.diplomski.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.entites.*;
import org.example.diplomski.data.enums.RoleType;
import org.example.diplomski.jwtUtils.ImageUtils;
import org.example.diplomski.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserLoaderService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ImageDataRepository imageDataRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository userProfileRepository;


    @Transactional
    public void loadUsers() {
        if (userRepository.count() == 0) {
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

                Role privateRole = roleRepository.findByRoleType(RoleType.PRIVATE).orElseThrow();
                Role adminRole = roleRepository.findByRoleType(RoleType.ADMIN).orElseThrow();

                byte[] compressedImage1 = img.getImageData();

//                User user2 = new User();
//                user2.setEmail("marko@gmail.com");
//                user2.setUsername("marko@gmail.com");
//                user2.setPassword(passwordEncoder.encode("marko"));
//                user2.setRole(privateRole);
//
//                UserProfile profile2 = new UserProfile();
//                ImageData userImg1 = ImageData.builder()
//                        .name("avatarcao.png")
//                        .type("image/png")
//                        .imageData(compressedImage1)
//                        .build();
//          //      imageDataRepository.save(userImg1);
//        //        profile2.setProfilePictureUrl(userImg1);
//                profile2.setCity("Belgrade");
//                profile2.setInterests(new ArrayList<>());
//                profile2.setUser(user2);
//
//
//                userProfileRepository.save(profile2);

//                User user3 = new User();
//                user3.setEmail("mirko@gmail.com");
//                user3.setUsername("mirko@gmail.com");
//                user3.setPassword(passwordEncoder.encode("mirko"));
//                user3.setRole(privateRole);
//
//
//                UserProfile profile1 = new UserProfile();
//          //      profile1.setProfilePictureUrl(img);
//                profile1.setCity("Belgrade");
//                profile1.setInterests(new ArrayList<>());
//                profile1.setUser(user3);
////
////                ImageData userImg2 = ImageData.builder()
////                        .name("avatar.png")
////                        .type("image/png")
////                        .imageData(compressedImage)
////                        .build();
////                imageDataRepository.save(userImg2);
////                profile1.setProfilePictureUrl(userImg2);
//                userProfileRepository.save(profile1);
//
//
//                User user1 = new User();
//                user1.setEmail("petar@gmail.com");
//                user1.setUsername("petar@gmail.com");
//                user1.setPassword(passwordEncoder.encode("petar"));
//                user1.setRole(adminRole);
//
//
//                UserProfile profile = new UserProfile();
//          //        profile.setProfilePictureUrl(img);
//                profile.setCity("Belgrade");
//                profile.setInterests(new ArrayList<>());
//                profile.setUser(user1);
//
//               userProfileRepository.save(profile);

                String[][] users = {
                        {"petar@gmail.com","petar@gmail.com","petar"},
                        {"marko@gmail.com","marko@gmail.com","marko"},
                        {"mirko@gmail.com","mirko@gmail.com","mirko"},
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

                byte[] compressedImage = img.getImageData();

                for (String[] userData : users) {
                    User user = new User();
                    user.setEmail(userData[0]);
                    user.setUsername(userData[1]);
                    user.setPassword(passwordEncoder.encode(userData[2]));
                    user.setRole(privateRole);
                    if(user.getEmail().equals("petar@gmail.com")) {
                        user.setRole(adminRole);
                    }


                    // Clone the image for each user
                    ImageData userImg = ImageData.builder()
                            .name("avatar.png")
                            .type("image/png")
                            .imageData(compressedImage)
                            .build();
                    imageDataRepository.save(userImg); // Save it

                    UserProfile profile3 = new UserProfile();
                    profile3.setProfilePictureUrl(userImg);
                    profile3.setCity("Belgrade");
                    profile3.setInterests(new ArrayList<>());
                    profile3.setUser(user);

                    userProfileRepository.save(profile3);

//                    profile2.setProfilePictureUrl(userImg);
//                    userProfileRepository.save(profile2);

                }
            } catch (IOException e) {
                throw new IllegalStateException("Cannot read default avatar", e);
            }




                }



        }
    }
}
