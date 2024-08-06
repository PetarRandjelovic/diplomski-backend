package org.example.diplomski.services;

import org.example.diplomski.data.dto.MediaDto;
import org.springframework.stereotype.Service;

@Service
public interface MediaService {

    MediaDto findById(Long id);
    MediaDto update(MediaDto mediaDto);
    Boolean delete(Long id);
    MediaDto save(MediaDto mediaDto);
    MediaDto findByPostId(Long postId);
    MediaDto findByUserId(Long userId);

}
