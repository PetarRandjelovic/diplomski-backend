package org.example.diplomski.services;

import org.example.diplomski.data.dto.profile.UserProfileRecord;
import org.example.diplomski.data.entites.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public interface ProfileService {

    UserProfileRecord findByEmail(String email);

    void storeProfilePicture(String email, MultipartFile file);

    Optional<UserProfile> findByUserEmail(String email);

     void detachProfilePicture(Long userProfileId);


}
