package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.MediaDto;
import org.example.diplomski.data.entites.Media;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.mapper.MediaMapper;
import org.example.diplomski.repositories.MediaRepository;
import org.example.diplomski.services.MediaService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    @Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
    }


    @Override
    public MediaDto findById(Long id) {
        Media media = mediaRepository.findById(id).orElseThrow(() -> new RuntimeException("Media not found"));
        return mediaMapper.mediaToMediaDto(media);
    }

    @Override
    public MediaDto update(MediaDto mediaDto) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
//        Media media = mediaRepository.findById(id).orElseThrow(() -> new NotFoundException("Media with id: " + id + " not found."));
//
//  //      postRepository.delete(post);
//
//        if (SpringSecurityUtil.hasRoleRole("ROLE_ADMIN")) {
//            mediaRepository.delete(id);
//            return true;
//        }
   //     if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
//            if (SpringSecurityUtil.getPrincipalEmail().equals(media.getPost().getUser().getEmail())) {
//                mediaRepository.delete(id);
//                return true;
//            }
//        }
//        throw new RuntimeException("You don't have permission to delete this post.");
        return true;
    }

    @Override
    public MediaDto save(MediaDto mediaDto) {
        return null;
    }

    @Override
    public MediaDto findByPostId(Long postId) {
        return null;
    }

    @Override
    public MediaDto findByUserId(Long userId) {
        return null;
    }
}
