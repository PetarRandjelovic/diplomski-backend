package org.example.diplomski.controllers;

import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.CommentDto;
import org.example.diplomski.services.CommentService;
import org.example.diplomski.services.PostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/comments",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)

public class CommentController {

    private final CommentService commentService;


    @GetMapping(path = "/commentsByPostId/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findCommendByPostId(@PathVariable Long id) {

        return ResponseEntity.ok(commentService.findCommentsByPostId(id));

    }

    @PostMapping(path = "/commentPostId/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> createCommentByPostId(@PathVariable Long id,
                                                   @RequestBody CommentDto commentDto,
                                                   @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(commentService.createPostByPostId(id,userDetails.getUsername(),commentDto.getContent()));

    }
}
