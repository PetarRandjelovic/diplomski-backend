package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.CommentDto;
import org.example.diplomski.data.entites.Comment;
import org.example.diplomski.data.entites.Media;
import org.example.diplomski.mapper.CommentMapper;
import org.example.diplomski.repositories.CommentRepository;
import org.example.diplomski.services.CommentService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDto findById(Long id) {

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        return commentMapper.commentToCommentDto(comment);
    }

    @Override
    public CommentDto update(CommentDto commentDto) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
//        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment with id: " + id + " not found."));
//
//        //      postRepository.delete(post);
//
//        if (SpringSecurityUtil.hasRoleRole("ROLE_ADMIN")) {
//            commentRepository.delete(id);
//            return true;
//        }
//        if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
//            if (SpringSecurityUtil.getPrincipalEmail().equals(comment.getPost().getUser().getEmail())) {
//                commentRepository.delete(id);
//                return true;
//            }
//        }
//        throw new RuntimeException("You don't have permission to delete this post.");

        return true;
    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        return null;
    }

    @Override
    public CommentDto findByPostId(Long postId) {
        return null;
    }

    @Override
    public CommentDto findByUserId(Long userId) {
        return null;
    }
}
