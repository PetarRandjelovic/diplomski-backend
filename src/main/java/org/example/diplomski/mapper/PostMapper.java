package org.example.diplomski.mapper;

import org.example.diplomski.data.dto.MediaDto;
import org.example.diplomski.data.dto.post.PostDto;
import org.example.diplomski.data.dto.TagDto;
import org.example.diplomski.data.dto.post.PostRecord;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.data.entites.Tag;
import org.example.diplomski.repositories.PostRepository;
import org.example.diplomski.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    private final UserRepository userRepository;
    private final MediaMapper mediaMapper;


    public PostMapper(UserRepository userRepository, MediaMapper mediaMapper) {
        this.userRepository = userRepository;
        this.mediaMapper = mediaMapper;
    }


    public PostDto postToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setUserEmail(post.getUser().getEmail());
        postDto.setContent(post.getContent());
        postDto.setTags(post.getTags().stream()
                .map(tag -> new TagDto(tag.getId(), tag.getName()))
                .collect(Collectors.toList()));
        postDto.setCreationDate(post.getCreationDate());
        postDto.setMedia(post.getMedia().stream().map(mediaMapper::mediaToMediaDto).toList());

        return postDto;
    }

    public Post postDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setUser(userRepository.findByEmail(postDto.getUserEmail()).get());
        post.setCreationDate(postDto.getCreationDate());
       // post.setMedia(postDto.getMedia().stream().map(mediaMapper::mediaDtoToMedia).toList());
        return post;
    }


    public PostRecord postToPostRecord(Post post) {

        List<MediaDto> list=post.getMedia().stream().map(mediaMapper::mediaToMediaDto).toList();

        return new PostRecord(post.getId(), post.getUser().getEmail(),post.getContent(),post.getTags().stream()
                .map(tag -> new TagDto(tag.getId(), tag.getName())).toList()
                ,post.getCreationDate(),post.getLikes().size(),list);
    }

}
