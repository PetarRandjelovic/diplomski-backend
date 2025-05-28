package org.example.diplomski.controllers;

import lombok.RequiredArgsConstructor;

import org.example.diplomski.services.ProfileService;
import org.example.diplomski.services.StorageService;
import org.example.diplomski.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@CrossOrigin
public class UserProfileController {

    private final ProfileService profileService;
        private final StorageService storageService;
    private final UserService userService;



    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = storageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName){
        byte[] imageData=storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @PostMapping("/{email}")
    public ResponseEntity<?> uploadToEmail(@PathVariable String email,@RequestParam("image")MultipartFile file) throws IOException {
        return ResponseEntity.ok( storageService.uploadToEmail(email,file));
    }


    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteImage(@PathVariable String name) {

        return ResponseEntity.ok( storageService.deleteImage(name));

    }



    @GetMapping("/email/{email}")
    public ResponseEntity<?> getProfilePicture(@PathVariable String email){
        byte[] imageData=storageService.getProfilePicture(email);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getProfilePictureById(@PathVariable Long id){
        byte[] imageData=storageService.getProfilePictureById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

}