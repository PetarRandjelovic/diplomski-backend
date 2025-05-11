package org.example.diplomski.mapper;

import javax.annotation.processing.Generated;
import org.example.diplomski.data.dto.LikeCommentDto;
import org.example.diplomski.data.entites.Comment;
import org.example.diplomski.data.entites.LikeComment;
import org.example.diplomski.data.entites.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-11T19:00:48+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class LikeCommentMapperImpl implements LikeCommentMapper {

    @Override
    public LikeCommentDto toDto(LikeComment likeComment) {
        if ( likeComment == null ) {
            return null;
        }

        LikeCommentDto likeCommentDto = new LikeCommentDto();

        likeCommentDto.setEmail( likeCommentUserEmail( likeComment ) );
        likeCommentDto.setCommentId( likeCommentCommentId( likeComment ) );
        likeCommentDto.setId( likeComment.getId() );

        return likeCommentDto;
    }

    @Override
    public LikeComment toEntity(LikeCommentDto likeCommentDto) {
        if ( likeCommentDto == null ) {
            return null;
        }

        LikeComment likeComment = new LikeComment();

        likeComment.setId( likeCommentDto.getId() );

        return likeComment;
    }

    private String likeCommentUserEmail(LikeComment likeComment) {
        if ( likeComment == null ) {
            return null;
        }
        User user = likeComment.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private Long likeCommentCommentId(LikeComment likeComment) {
        if ( likeComment == null ) {
            return null;
        }
        Comment comment = likeComment.getComment();
        if ( comment == null ) {
            return null;
        }
        Long id = comment.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
