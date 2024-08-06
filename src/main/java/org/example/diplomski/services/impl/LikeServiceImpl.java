package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.LikeDto;
import org.example.diplomski.data.entites.Like;
import org.example.diplomski.data.entites.Media;
import org.example.diplomski.mapper.LikeMapper;
import org.example.diplomski.repositories.LikeRepository;
import org.example.diplomski.services.LikeService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    @Autowired
    public LikeServiceImpl( LikeRepository likeRepository, LikeMapper likeMapper) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;

    }


    @Override
    public void likePost(Long postId, Long userId) {

    }

    @Override
    public void unlikePost(Long postId, Long userId) {

    }

    @Override
    public void likeComment(Long commentId, Long userId) {

    }

    @Override
    public void unlikeComment(Long commentId, Long userId) {

    }

    @Override
    public boolean isPostLikedByUser(Long postId, Long userId) {
        return false;
    }

    @Override
    public boolean isCommentLikedByUser(Long commentId, Long userId) {
        return false;
    }

    @Override
    public LikeDto findByPostId(Long postId) {
        return null;
    }

    @Override
    public LikeDto findByCommentId(Long commentId) {
        return null;
    }

    @Override
    public LikeDto findByUserId(Long userId) {
        return null;
    }

    @Override
    public LikeDto findById(Long id) {
        return null;
    }

    @Override
    public LikeDto update(LikeDto likeDto) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
//        Like like = likeRepository.findById(id).orElseThrow(() -> new NotFoundException("Like with id: " + id + " not found."));
//
//        //      postRepository.delete(post);
//
//        if (SpringSecurityUtil.hasRoleRole("ROLE_ADMIN")) {
//            likeRepository.delete(id);
//            return true;
//        }
//        if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
//            if (SpringSecurityUtil.getPrincipalEmail().equals(like.getUser().getEmail())) {
//                likeRepository.delete(id);
//                return true;
//            }
//        }
//        throw new RuntimeException("You don't have permission to delete this post.");
        return true;
    }
}
