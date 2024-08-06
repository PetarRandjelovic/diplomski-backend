package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.MediaDto;
import org.example.diplomski.data.entites.Media;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    public MediaDto mediaToMediaDto(Media media) {
        MediaDto mediaDto = new MediaDto();
        mediaDto.setId(media.getId());
        mediaDto.setPostId(media.getPost().getId());
        mediaDto.setUrl(media.getUrl());
        return mediaDto;
    }
}
