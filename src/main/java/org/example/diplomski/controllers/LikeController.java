package org.example.diplomski.controllers;

import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.LikeCommentDto;
import org.example.diplomski.data.dto.LikeDto;
import org.example.diplomski.data.dto.LikePostDto;
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
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class LikeController {

    private final LikeService likeService;

    @PostMapping(path = "/like-post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> likePost(@RequestBody LikePostDto likePostDto) {
        return ResponseEntity.ok(likeService.likePost(likePostDto));
    }

    @PostMapping(path = "/like-comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> likeComment(@RequestBody LikeCommentDto likeCommentDto) {
        return ResponseEntity.ok(likeService.likeComment(likeCommentDto));
    }

    @GetMapping(path = "/post/{postId}")
    public ResponseEntity<?> getPostLikes(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.getPostLikesCount(postId));
    }

    @GetMapping(path = "/comment/{commentId}")
    public ResponseEntity<?> getCommentLikes(@PathVariable Long commentId) {
        return ResponseEntity.ok(likeService.getCommentLikesCount(commentId));
    }

    @GetMapping("/post/{postId}/liked")
    public ResponseEntity<?> hasUserLikedPost(@PathVariable Long postId, @RequestParam String email) {
        boolean liked = likeService.hasUserLikedPost(postId, email);
        return ResponseEntity.ok(liked);
    }

    @GetMapping("/comment/{commentId}/liked")
    public ResponseEntity<?> hasUserLikedComment(@PathVariable Long commentId, @RequestParam String email) {
        boolean liked = likeService.hasUserLikedComment(commentId, email);
        return ResponseEntity.ok(liked);
    }
}
