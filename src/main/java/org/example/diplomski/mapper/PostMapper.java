package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.PostDto;
import org.example.diplomski.data.entites.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDto postToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setUserEmail(post.getUser().getEmail());
        postDto.setContent(post.getContent());

        return postDto;
    }

}
