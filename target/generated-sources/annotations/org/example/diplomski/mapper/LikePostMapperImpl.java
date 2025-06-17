package org.example.diplomski.mapper;

import javax.annotation.processing.Generated;
import org.example.diplomski.data.dto.like.LikePostDto;
import org.example.diplomski.data.entites.LikePost;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.data.entites.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-11T23:22:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class LikePostMapperImpl implements LikePostMapper {

    @Override
    public LikePostDto toDto(LikePost likePost) {
        if ( likePost == null ) {
            return null;
        }

        LikePostDto likePostDto = new LikePostDto();

        likePostDto.setEmail( likePostUserEmail( likePost ) );
        likePostDto.setPostId( likePostPostId( likePost ) );
        likePostDto.setId( likePost.getId() );

        return likePostDto;
    }

    @Override
    public LikePost toEntity(LikePostDto likePostDto) {
        if ( likePostDto == null ) {
            return null;
        }

        LikePost likePost = new LikePost();

        likePost.setId( likePostDto.getId() );

        return likePost;
    }

    private String likePostUserEmail(LikePost likePost) {
        if ( likePost == null ) {
            return null;
        }
        User user = likePost.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private Long likePostPostId(LikePost likePost) {
        if ( likePost == null ) {
            return null;
        }
        Post post = likePost.getPost();
        if ( post == null ) {
            return null;
        }
        Long id = post.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
