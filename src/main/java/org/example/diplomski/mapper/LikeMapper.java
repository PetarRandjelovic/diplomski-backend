package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.LikeDto;
import org.example.diplomski.data.entites.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public LikeDto likeToLikeDto(Like like) {
        LikeDto likeDto = new LikeDto();
        likeDto.setId(like.getId());
        likeDto.setPostId(like.getPost().getId());
        likeDto.setEmail(like.getUser().getEmail());
        return likeDto;
    }

}
