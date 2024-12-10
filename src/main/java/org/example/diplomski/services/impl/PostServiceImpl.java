package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.PostDto;
import org.example.diplomski.data.dto.UserRelationshipDto;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.mapper.PostMapper;
import org.example.diplomski.repositories.PostRepository;
import org.example.diplomski.services.PostService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }


    @Override
    public PostDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
           return postMapper.postToPostDto(post);
    }

    @Override
    public PostDto update(PostDto postDto) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post with id: " + id + " not found."));
        if (SpringSecurityUtil.hasRoleRole("ROLE_ADMIN")) {
            postRepository.delete(post);
            return true;
        }
        if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(post.getUser().getEmail())) {
                postRepository.delete(post);
                return true;
            }
        }
        throw new RuntimeException("You don't have permission to delete this post.");
    }

    @Override
    public PostDto save(PostDto postDto) {
        return null;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = postMapper.postDtoToPost(postDto);

        if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(postDto.getUserEmail())) {
                postRepository.save(post);
            }
        }


        return postMapper.postToPostDto(post);
    }

    @Override
    public List<PostDto> findAll() {


        List<Post> posts = postRepository.findAll();

        return posts.stream().map(postMapper::postToPostDto).toList();
    }
}
