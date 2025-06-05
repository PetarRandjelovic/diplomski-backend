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

                String[][] users = {
                        {"petar@gmail.com","petar","petar"},
                        {"marko@gmail.com","markoo","marko"},
                        {"mirko@gmail.com","mirkov","mirko"},
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
