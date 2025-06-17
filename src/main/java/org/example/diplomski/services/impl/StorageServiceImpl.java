package org.example.diplomski.services.impl;

import jakarta.transaction.Transactional;
import org.example.diplomski.data.entites.ImageData;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.entites.UserProfile;
import org.example.diplomski.exceptions.EmailNotFoundException;
import org.example.diplomski.jwtUtils.ImageUtils;
import org.example.diplomski.repositories.ImageDataRepository;
import org.example.diplomski.repositories.UserProfileRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.DeleteImageService;
import org.example.diplomski.services.ProfileService;
import org.example.diplomski.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {


    private final ImageDataRepository repository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final ImageDataRepository imageDataRepository;
    private final DeleteImageService deleteImageService;
    private final ProfileService profileService;
    private final ProfileService storageService;

    public StorageServiceImpl(ImageDataRepository repository,
                              UserRepository userRepository,
                              UserProfileRepository userProfileRepository,
                              ImageDataRepository imageDataRepository,
                              DeleteImageService deleteImageService, ProfileService profileService,ProfileService storageService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.imageDataRepository = imageDataRepository;
        this.deleteImageService = deleteImageService;
        this.profileService = profileService;
        this.storageService = storageService;
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }
    @Transactional
    @Override
    public byte[] downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findById(1L);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    @Transactional
    @Override
    public boolean uploadToEmail(String email, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Long userId = user.getId();

        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("UserProfile for user ID " + userId + " not found."));

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

        ImageData oldImage = userProfile.getProfilePictureUrl();

        if (oldImage != null) {
            profileService.detachProfilePicture(userProfile.getId());
            deleteImageService.deleteImageById(oldImage.getId());
        }

        userProfile.setProfilePictureUrl(imageData);
        userProfileRepository.save(userProfile);

        return true;
    }


    @Transactional
    @Override
    public byte[] getProfilePicture(String email) {

        User user=userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("User not found"));
        UserProfile userProfile=userProfileRepository.findByUserEmail(email).orElseThrow(()->new NotFoundException("UserProfile for user email " + email + " not found."));

        byte[] imageData=ImageUtils.decompressImage(userProfile.getProfilePictureUrl().getImageData());

        return imageData;
    }

    @Transactional
    @Override
    public boolean deleteImage(String name) {
        ImageData dbImageData = repository.findByName(name).orElseThrow(()->new EmailNotFoundException(name));

        imageDataRepository.delete(dbImageData);

        return true;
    }

    @Transactional
    @Override
    public byte[] getProfilePictureById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        UserProfile userProfile=userProfileRepository.findByUserId(id).orElseThrow(()->new NotFoundException("UserProfile for user email " + id + " not found."));

        byte[] imageData=ImageUtils.decompressImage(userProfile.getProfilePictureUrl().getImageData());

        return imageData;
    }

    @Transactional
    public void deleteImageById(Long id) {
        ImageData dbImageData = repository.findById(id).orElseThrow(()->new EmailNotFoundException(id.toString()));
        imageDataRepository.delete(dbImageData);
    }
}