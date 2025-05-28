package org.example.diplomski.controllers;


import org.example.diplomski.services.TagService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(
        value = "/api/tags",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class TagController {


    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping(path = "/all", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getAllTags() {

        return ResponseEntity.ok(tagService.findAll());
    }



}
