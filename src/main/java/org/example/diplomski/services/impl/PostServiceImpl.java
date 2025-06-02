package org.example.diplomski.services.impl;

import org.example.diplomski.data.dto.MediaDto;
import org.example.diplomski.data.dto.post.PostDto;
import org.example.diplomski.data.dto.TagDto;
import org.example.diplomski.data.dto.post.PostRecord;
import org.example.diplomski.data.entites.Media;
import org.example.diplomski.data.entites.Post;
import org.example.diplomski.data.entites.Tag;
import org.example.diplomski.data.entites.User;
import org.example.diplomski.data.enums.MediaType;
import org.example.diplomski.exceptions.PostNotFoundException;
import org.example.diplomski.exceptions.TagNotFoundException;
import org.example.diplomski.exceptions.UserEmailNotFoundException;
import org.example.diplomski.exceptions.UserIdNotFoundException;
import org.example.diplomski.mapper.PostMapper;
import org.example.diplomski.repositories.MediaRepository;
import org.example.diplomski.repositories.PostRepository;
import org.example.diplomski.repositories.TagRepository;
import org.example.diplomski.repositories.UserRepository;
import org.example.diplomski.services.PostService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           PostMapper postMapper, TagRepository tagRepository,
                           UserRepository userRepository,
                           MediaRepository mediaRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
    }


    @Override
    public PostDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        System.out.println(post.getMedia());
        PostDto postRecord=postMapper.postToPostDto(post);
        System.out.println(postRecord.getMedia());
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

        System.out.println("brate");
        Post post = postMapper.postDtoToPost(postDto);

        System.out.println("really");
        //  if (SpringSecurityUtil.hasRoleRole("ROLE_USER") || SpringSecurityUtil.hasRoleRole("ROLE_PRIVATE") || SpringSecurityUtil.hasRoleRole("ROLE_PUBLIC")) {
        if (SpringSecurityUtil.getPrincipalEmail().equals(postDto.getUserEmail())) {
            List<Tag> tags = new ArrayList<>();
            for (TagDto tagDto : postDto.getTags()) {
                System.out.println(tagDto.getName());
                Tag tag = tagRepository.findByName(tagDto.getName())
                        .orElseThrow(()->new TagNotFoundException("Tag with "+tagDto.getName()+" is not found"));
                tags.add(tag);
            }

            post.setCreationDate(Instant.now().toEpochMilli());
            post.setTags(tags);
            System.out.println("test0");
            postRepository.save(post);
            System.out.println("test00");
            // Handle multiple media
            if (postDto.getMedia() != null && !postDto.getMedia().isEmpty()) {
                List<Media> mediaList = new ArrayList<>();

                for (MediaDto mediaDto : postDto.getMedia()) {
                    Media media = new Media();
                    media.setPost(post);

                    String originalUrl = mediaDto.getUrl();

                    // Auto-detect media type from URL
                    MediaType detectedType = detectMediaType(originalUrl);

                    // Convert URL if it's a video platform
                    String processedUrl = convertVideoUrl(originalUrl);

                    media.setUrl(processedUrl);
                    media.setType(detectedType);
                    media.setTitle(mediaDto.getTitle() != null ? mediaDto.getTitle() : "Media");
                    // media.setDisplayOrder(mediaList.size()); // Set order

                    mediaList.add(media);

                    System.out.println("Original URL: " + originalUrl);
                    System.out.println("Processed URL: " + processedUrl);
                    System.out.println("Detected Type: " + detectedType);
                }

                // Save all media
                mediaRepository.saveAll(mediaList);
                post.setMedia(mediaList);
                postRepository.save(post);


            }
            //   }



        }
        System.out.println("test");
        return postMapper.postToPostDto(post);
    }

    // Enhanced method to detect media type from URL
    private MediaType detectMediaType(String url) {
        String lowerUrl = url.toLowerCase();

        // Video platforms
        if (lowerUrl.contains("youtube.com") || lowerUrl.contains("youtu.be")) {
            return MediaType.VIDEO;
        }
        if (lowerUrl.contains("vimeo.com")) {
            return MediaType.VIDEO;
        }
        if (lowerUrl.contains("dailymotion.com")) {
            return MediaType.VIDEO;
        }
        if (lowerUrl.contains("twitch.tv")) {
            return MediaType.VIDEO;
        }

        // Direct video file extensions
        if (lowerUrl.endsWith(".mp4") || lowerUrl.endsWith(".webm") ||
                lowerUrl.endsWith(".avi") || lowerUrl.endsWith(".mov") ||
                lowerUrl.endsWith(".wmv") || lowerUrl.endsWith(".flv") ||
                lowerUrl.endsWith(".mkv") || lowerUrl.endsWith(".m4v")) {
            return MediaType.VIDEO;
        }

        // Image file extensions
        if (lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg") ||
                lowerUrl.endsWith(".png") || lowerUrl.endsWith(".gif") ||
                lowerUrl.endsWith(".bmp") || lowerUrl.endsWith(".webp") ||
                lowerUrl.endsWith(".svg") || lowerUrl.endsWith(".tiff")) {
            return MediaType.IMAGE;
        }

        // Image hosting platforms
        if (lowerUrl.contains("imgur.com") || lowerUrl.contains("flickr.com") ||
                lowerUrl.contains("instagram.com") || lowerUrl.contains("pinterest.com")) {
            return MediaType.IMAGE;
        }

        // Default to image if uncertain
        return MediaType.IMAGE;
    }

    // Enhanced method to convert video URLs
    private String convertVideoUrl(String url) {
        String lowerUrl = url.toLowerCase();

        // YouTube conversions
        if (lowerUrl.contains("youtube.com/watch?v=")) {
            String videoId = url.split("v=")[1].split("&")[0];
            return "https://www.youtube.com/embed/" + videoId;
        }
        if (lowerUrl.contains("youtu.be/")) {
            String videoId = url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];
            return "https://www.youtube.com/embed/" + videoId;
        }

        // Vimeo conversions
        if (lowerUrl.contains("vimeo.com/")) {
            String videoId = url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];
            return "https://player.vimeo.com/video/" + videoId;
        }

        // Dailymotion conversions
        if (lowerUrl.contains("dailymotion.com/video/")) {
            String videoId = url.substring(url.indexOf("/video/") + 7).split("\\?")[0];
            return "https://www.dailymotion.com/embed/video/" + videoId;
        }

        // Return original URL for direct files and images
        return url;
    }

    @Override
    public List<PostRecord> findAll() {


        List<Post> posts = postRepository.findAll();

        return posts.stream().map(postMapper::postToPostRecord).toList();
    }

    @Override
    public List<PostDto> findByEmail(String email) {

        User user=userRepository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email));


        List<Post> postList = postRepository.findAllByUserId(user.getId()).stream().toList();

        return postList.stream().map(postMapper::postToPostDto).toList();
    }

    @Override
    public List<PostRecord> findByTag(List<String> tag) {

        if(tag.isEmpty()){
            return findAll();
        }
        List<Post> listPosts=postRepository.findByTags(tag);

        return listPosts.stream().map(postMapper::postToPostRecord).toList();
    }

    @Override
    public List<PostDto> findByUserId(Long id) {
        User user=userRepository.findById(id).orElseThrow(() -> new UserIdNotFoundException(id.toString()));

        List<Post> postList = postRepository.findAllByUserId(user.getId()).stream().toList();

        return postList.stream().map(postMapper::postToPostDto).toList();
    }
}
