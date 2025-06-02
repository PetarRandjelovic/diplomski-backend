package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.MediaDto;
import org.example.diplomski.data.entites.Media;
import org.example.diplomski.exceptions.PostNotFoundException;
import org.example.diplomski.repositories.PostRepository;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    private PostRepository postRepository;


    public MediaMapper(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public MediaDto mediaToMediaDto(Media media) {
        MediaDto mediaDto = new MediaDto();
        mediaDto.setId(media.getId());
        mediaDto.setPostId(media.getPost().getId());
        mediaDto.setUrl(media.getUrl());
        mediaDto.setTitle(media.getTitle());
        mediaDto.setType(media.getType());
        return mediaDto;
    }

    public Media mediaDtoToMedia(MediaDto mediaDto) {
        Media media = new Media();
          media.setUrl(mediaDto.getUrl());
        media.setTitle(mediaDto.getTitle());
        media.setType(mediaDto.getType());
        return media;
    }
}
