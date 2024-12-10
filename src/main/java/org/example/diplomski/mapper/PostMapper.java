package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.PostDto;
import org.example.diplomski.data.dto.TagDto;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.data.entites.Tag;
import org.example.diplomski.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    private UserRepository userRepository;

    @Autowired
    public PostMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public PostDto postToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setUserEmail(post.getUser().getEmail());
        postDto.setContent(post.getContent());
        postDto.setTags(post.getTags().stream()
                .map(tag -> new TagDto(tag.getId(), tag.getName()))
                .collect(Collectors.toList()));
        postDto.setCreationDate(post.getCreationDate());

        return postDto;
    }

    public Post postDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setContent(postDto.getContent());
        System.out.println(postDto.getUserEmail());
        System.out.println(userRepository.findByEmail(postDto.getUserEmail()));
        post.setUser(userRepository.findByEmail(postDto.getUserEmail()).get());
        List<Tag> tags = postDto.getTags().stream()
                .map(tagDto -> new Tag(tagDto.getId(), tagDto.getName()))
                .collect(Collectors.toList());
        post.setTags(tags);
        post.setCreationDate(postDto.getCreationDate());
        return post;
    }

}
