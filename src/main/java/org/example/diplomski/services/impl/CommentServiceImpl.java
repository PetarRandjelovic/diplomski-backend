package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.CommentDto;
import org.example.diplomski.data.entites.Comment;
import org.example.diplomski.data.entites.Media;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.exceptions.PostNotFoundException;
import org.example.diplomski.exceptions.UserEmailNotFoundException;
import org.example.diplomski.exceptions.UserIdNotFoundException;
import org.example.diplomski.mapper.CommentMapper;
import org.example.diplomski.repositories.CommentRepository;
import org.example.diplomski.repositories.PostRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.CommentService;
import org.example.diplomski.services.MediaService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper,
            PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment with id: " + id + " not found."));

        if (SpringSecurityUtil.hasRoleRole("ROLE_ADMIN")) {
            commentRepository.delete(comment);
            return true;
        }
        if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(comment.getUser().getEmail())) {
                commentRepository.delete(comment);
                return true;
            }
            if (SpringSecurityUtil.getPrincipalEmail().equals(comment.getPost().getUser().getEmail())) {
                commentRepository.delete(comment);
                return true;
            }
        }
        throw new RuntimeException("You don't have permission to delete this post.");

    }

    @Override
    public CommentDto save(CommentDto commentDto) {
        return null;
    }

    @Override
    public List<CommentDto> findCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream()
                .map(commentMapper::commentToCommentDto)
                .toList();
    }

    @Override
    public CommentDto findByUserId(Long userId) {
        return null;
    }

    @Override
    public CommentDto createPostByPostId(Long postId, String username,String comment) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserEmailNotFoundException(username));

        Comment createdComment = new Comment();
        createdComment.setPost(post);
        createdComment.setUser(user);
        createdComment.setContent(comment);
        commentRepository.save(createdComment);
        return commentMapper.commentToCommentDto(createdComment);
    }
}
