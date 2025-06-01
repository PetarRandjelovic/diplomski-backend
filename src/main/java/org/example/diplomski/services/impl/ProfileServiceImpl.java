package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.profile.UserProfileRecord;
import org.example.diplomski.data.entites.UserProfile;
import org.example.diplomski.repositories.UserProfileRepository;
import org.example.diplomski.services.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserProfileRepository userProfileRepository;


    private final Path uploadRoot = Paths.get("uploads/profile-pictures");


    public ProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }


    @Override
    public UserProfileRecord findByEmail(String email) {

      //  UserProfile userProfile = userProfileRepository.

        return null;
    }
    @Transactional
    @Override
    public void storeProfilePicture(String email, MultipartFile file) {


    }

    @Override
    public Optional<UserProfile> findByUserEmail(String email) {
        return Optional.empty();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void detachProfilePicture(Long userProfileId) {
        UserProfile profile = userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new NotFoundException("UserProfile not found"));
        profile.setProfilePictureUrl(null);
        userProfileRepository.save(profile);
        userProfileRepository.flush();
    }

}
