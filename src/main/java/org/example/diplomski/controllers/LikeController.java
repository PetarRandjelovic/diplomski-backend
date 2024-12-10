package org.example.diplomski.controllers;

import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.LikeDto;
import org.example.diplomski.data.dto.PostDto;
import org.example.diplomski.services.LikeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/like",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class LikeController {

    private final LikeService likeService;

    @PostMapping(path = "/like-post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> likePost(@RequestBody LikeDto likeDto) {
        return ResponseEntity.ok(likeService.likePost(likeDto));
    }

    @PostMapping(path = "/like-comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> likeComment(@RequestBody LikeDto likeDto) {
        return ResponseEntity.ok(likeService.likeComment(likeDto));
    }
}
