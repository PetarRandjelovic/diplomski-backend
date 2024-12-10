package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.LikeDto;
import org.example.diplomski.data.entites.Comment;
import org.example.diplomski.data.entites.Like;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.mapper.LikeMapper;
import org.example.diplomski.repositories.CommentRepository;
import org.example.diplomski.repositories.LikeRepository;
import org.example.diplomski.repositories.PostRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.LikeService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, LikeMapper likeMapper, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }


    @Override
    public LikeDto likePost(LikeDto likeDto) {
        User user = userRepository.findByEmail(likeDto.getEmail()).orElseThrow(() -> new NotFoundException("User with id: " + likeDto.getEmail() + " not found."));
        Post post = postRepository.findById(likeDto.getPostId()).orElseThrow(() -> new NotFoundException("Post with id: " + likeDto.getPostId() + " not found."));
        Like existingLike = likeRepository.findByPostIdAndUserId(post.getId(), user.getId());
        if (existingLike != null) {
            likeRepository.delete(existingLike);
            return null;
        }
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        likeRepository.save(like);

        return likeMapper.toDto(like);
    }

    @Override
    public LikeDto likeComment(LikeDto likeDto) {

        User user = userRepository.findByEmail(likeDto.getEmail()).orElseThrow(() -> new NotFoundException("User with id: " + likeDto.getEmail() + " not found."));
        Comment comment = commentRepository.findById(likeDto.getCommentId()).orElseThrow(() -> new NotFoundException("Comment with id: " + likeDto.getCommentId() + " not found."));

        Like existingLike = likeRepository.findByCommentIdAndUserId(comment.getId(), user.getId());
        if (existingLike != null) {
            likeRepository.delete(existingLike);
            return null;
        }

        Like like = new Like();
        like.setComment(comment);
        like.setUser(user);
        likeRepository.save(like);

        return likeMapper.toDto(like);
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
        Like like = likeRepository.findById(id).orElseThrow(() -> new NotFoundException("Like with id: " + id + " not found."));

        if (SpringSecurityUtil.hasRoleRole("ROLE_ADMIN")) {
            likeRepository.delete(like);
            return true;
        }
        if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(like.getUser().getEmail())) {
                likeRepository.delete(like);
                return true;
            }
        }
        throw new RuntimeException("You don't have permission to delete this post.");

    }
}
