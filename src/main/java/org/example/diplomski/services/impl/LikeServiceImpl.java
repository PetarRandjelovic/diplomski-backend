package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.like.LikeCommentDto;
import org.example.diplomski.data.dto.LikeDto;
import org.example.diplomski.data.dto.like.LikePostDto;
import org.example.diplomski.data.entites.*;
import org.example.diplomski.mapper.LikeCommentMapper;
import org.example.diplomski.mapper.LikeMapper;
import org.example.diplomski.mapper.LikePostMapper;
import org.example.diplomski.repositories.*;
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
    private final LikeCommentRepository likeCommentRepository;
    private final LikePostRepository likePostRepository;
    private final LikePostMapper likePostMapper;
    private final LikeCommentMapper likeCommentMapper;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, LikeMapper likeMapper, UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository,
                           LikeCommentRepository likeCommentRepository, LikePostRepository likePostRepository, LikePostMapper likePostMapper, LikeCommentMapper likeCommentMapper) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeCommentRepository = likeCommentRepository;
        this.likePostRepository = likePostRepository;
        this.likePostMapper = likePostMapper;
        this.likeCommentMapper = likeCommentMapper;

    }


    @Override
    public LikePostDto likePost(LikePostDto likePostDto) {
        User user = userRepository.findByEmail(likePostDto.getEmail()).orElseThrow(() -> new NotFoundException("User with id: " + likePostDto.getEmail() + " not found."));
        Post post = postRepository.findById(likePostDto.getPostId()).orElseThrow(() -> new NotFoundException("Post with id: " + likePostDto.getPostId() + " not found."));
        boolean existingLike = likePostRepository.existsByPostIdAndUserId(post.getId(), user.getId());
        System.out.println("existingLike: " + existingLike);
        if (existingLike) {
            LikePost likePost = likePostRepository.findByPostIdAndUserId(likePostDto.getPostId(), user.getId());
            likePostRepository.delete(likePost);
            return null;
        }
        LikePost like = new LikePost();
        like.setPost(post);
        like.setUser(user);
        likePostRepository.save(like);

        return likePostMapper.toDto(like);
    }

    @Override
    public LikeCommentDto likeComment(LikeCommentDto likeCommentDto) {

        User user = userRepository.findByEmail(likeCommentDto.getEmail()).orElseThrow(() -> new NotFoundException("User with id: " + likeCommentDto.getEmail() + " not found."));
        Comment comment = commentRepository.findById(likeCommentDto.getCommentId()).orElseThrow(() -> new NotFoundException("Comment with id: " + likeCommentDto.getCommentId() + " not found."));

        boolean existingLike = likeCommentRepository.existsByCommentIdAndUserId(comment.getId(), user.getId());

        if (existingLike) {
            likeCommentRepository.delete(likeCommentMapper.toEntity(likeCommentDto));
            return null;
        }

        LikeComment like = new LikeComment();
        like.setComment(comment);
        like.setUser(user);
        likeCommentRepository.save(like);

        return likeCommentMapper.toDto(like);
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

    @Override
    public int getPostLikesCount(Long postId) {
        return likePostRepository.findByPostId(postId).size();
    }

    @Override
    public int getCommentLikesCount(Long commentId) {

        return likeCommentRepository.findByCommentId(commentId).size();
    }

    @Override
    public boolean hasUserLikedPost(Long postId, String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return false;
        return likePostRepository.existsByPostIdAndUserId(postId, user.getId());
    }

    @Override
    public boolean hasUserLikedComment(Long commentId, String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) return false;
        return likeCommentRepository.existsByCommentIdAndUserId(commentId, user.getId());
    }

}
