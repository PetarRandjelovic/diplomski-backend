package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.LikeDto;
import org.example.diplomski.data.entites.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public LikeDto toDto(Like like) {
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        if(like.getPost() != null) {
            likeDto.setPostId(like.getPost().getId());
        }
        likeDto.setEmail(like.getUser().getEmail());
        if(like.getComment() != null) {
            likeDto.setCommentId(like.getComment().getId());
        }
        return likeDto;
    }

}
