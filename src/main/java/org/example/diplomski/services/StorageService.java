package org.example.diplomski.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface StorageService {
    String uploadImage(MultipartFile file) throws IOException;

    byte[] downloadImage(String fileName);

    boolean uploadToEmail(String email,MultipartFile file) throws IOException;

    byte[] getProfilePicture(String email);

    boolean deleteImage(String name);
}
