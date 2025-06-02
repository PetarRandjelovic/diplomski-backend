package org.example.diplomski.controllers;


import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.dto.post.PostDto;
import org.example.diplomski.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/posts",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PostController {

    private final PostService postService;


    @DeleteMapping(path = "/delete/{id}", consumes = MediaType.ALL_VALUE)
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER','ROLE_PRIVATE','ROLE_PUBLIC')")
    @Transactional
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(postService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping(path = "/id/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long id) {
        PostDto postDto = postService.findById(id);

        return ResponseEntity.ok(postDto);
    }

    @GetMapping(path = "/email/{email}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findByUserEmail(@PathVariable String email) {
        return ResponseEntity.ok(postService.findByEmail(email));
    }

    @GetMapping(path = "/userId/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> findByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findByUserId(id));
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER','ROLE_PRIVATE','ROLE_PUBLIC')")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        System.out.println("test01");
        try {
            return ResponseEntity.ok(postService.createPost(postDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping(path = "/all", consumes = MediaType.ALL_VALUE)
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER','ROLE_PRIVATE','ROLE_PUBLIC')")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping(path = "/tags")
    public ResponseEntity<?> findByTags(@RequestParam List<String> tags) {
        return ResponseEntity.ok(postService.findByTag(tags));
    }
}
