package org.example.diplomski.mapper;


import org.example.diplomski.data.dto.like.LikeCommentDto;
import org.example.diplomski.data.entites.LikeComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeCommentMapper {


    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "comment.id", target = "commentId")
    LikeCommentDto toDto(LikeComment likeComment);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comment", ignore = true)
    LikeComment toEntity(LikeCommentDto likeCommentDto);
}
