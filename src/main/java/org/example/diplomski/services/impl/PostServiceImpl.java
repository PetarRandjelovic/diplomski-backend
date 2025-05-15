package org.example.diplomski.services.impl;

import io.swagger.v3.oas.annotations.tags.Tags;
import org.example.diplomski.data.dto.PostDto;
import org.example.diplomski.data.dto.TagDto;
import org.example.diplomski.data.dto.UserRelationshipDto;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.data.entites.Tag;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.exceptions.PostNotFoundException;
import org.example.diplomski.exceptions.UserEmailNotFoundException;
import org.example.diplomski.mapper.PostMapper;
import org.example.diplomski.repositories.PostRepository;
import org.example.diplomski.repositories.TagRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.PostService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, TagRepository tagRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }


    @Override
    public PostDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
           return postMapper.postToPostDto(post);
    }

    @Override
    public PostDto update(PostDto postDto) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
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

      //  if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(postDto.getUserEmail())) {
                List<Tag> tags= new ArrayList<>();

                for (TagDto tagDto : postDto.getTags()) {
                    System.out.println(tagDto.getName());
                   Tag tag = tagRepository.findByName(tagDto.getName())
                            .orElseGet(() -> tagRepository.save(new Tag(null, tagDto.getName())));
                    tags.add(tag);
                }
                post.setTags(tags);

                postRepository.save(post);
            }
     //   }


        return postMapper.postToPostDto(post);
    }

    @Override
    public List<PostDto> findAll() {


        List<Post> posts = postRepository.findAll();

        return posts.stream().map(postMapper::postToPostDto).toList();
    }

    @Override
    public List<PostDto> findByEmail(String email) {

        User user=userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email));

        List<Post> postList=postRepository.findByUserEmail(user.getEmail()).stream().toList();


        return postList.stream().map(postMapper::postToPostDto).toList();
    }
}
