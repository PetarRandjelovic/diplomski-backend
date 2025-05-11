package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.LikePostDto;
import org.example.diplomski.data.entites.LikePost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikePostMapper {

    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "post.id", target = "postId")
    LikePostDto toDto(LikePost likePost);



    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    LikePost toEntity(LikePostDto likePostDto);
}
