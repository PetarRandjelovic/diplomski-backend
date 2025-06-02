package org.example.diplomski.bootstrap;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.diplomski.data.entites.*;
import org.example.diplomski.data.enums.RelationshipStatus;
import org.example.diplomski.data.enums.RoleType;
import org.example.diplomski.exceptions.UserEmailNotFoundException;
import org.example.diplomski.jwtUtils.ImageUtils;
import org.example.diplomski.repositories.*;
import org.example.diplomski.services.StorageService;
import org.example.diplomski.utils.SpringSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapData.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRelationshipRepository userRelationshipRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final UserProfileRepository userProfileRepository;
    private final ImageDataRepository imageDataRepository;
    private final UserLoaderService userLoaderService;
    private final LikePostRepository likePostRepository;

    public void run(String... args) {
        try {
            logger.info("USERService: DEV DATA LOADING IN PROGRESS...");


            loadTags();
            loadRoles();
          //  loadImages();
        //    loadUsers();
            userLoaderService.loadUsers();
        //    loadProfiles();
            loadUserRelationships();
            loadPosts();
            loadComments();
            loadLikes();
            loadMedia();


            logger.info("USERService: DEV DATA LOADING FINISHED...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMedia() {

    }

    private void loadLikes() {
        if (likePostRepository.count() == 0) {
            List<Post> allPosts = postRepository.findAll();
            List<User> allUsers = userRepository.findAll();
            Random random = new Random();

            for (Post post : allPosts) {
                // Exclude the post's author from likers
                List<User> availableUsers = allUsers.stream()
                        .filter(user -> !user.getId().equals(post.getUser().getId()))
                        .collect(Collectors.toList());

                if (availableUsers.isEmpty()) continue;

                // Cap number of likes to available users
                int numberOfLikes = random.nextInt(availableUsers.size() + 1); // +1 because nextInt is exclusive

                // Shuffle to get unique random users
                Collections.shuffle(availableUsers);

                for (int i = 0; i < numberOfLikes; i++) {
                    User user = availableUsers.get(i);

                    LikePost likePost = new LikePost();
                    likePost.setPost(post);
                    likePost.setUser(user);

                    likePostRepository.save(likePost);
                }
            }
        }
    }



    private void loadImages() {
        if (imageDataRepository.count() == 0) {
            try (InputStream in =
                         getClass().getResourceAsStream("/avatars/avatar.png")) {

                if (in == null) {
                    throw new IllegalStateException(
                            "avatars/avatar.png not on class-path");
                }

                ImageData img = ImageData.builder()
                        .name("avatar.png")
                        .type("image/png")
                        .imageData(ImageUtils.compressImage(in.readAllBytes()))
                        .build();

                imageDataRepository.save(img);


            } catch (IOException e) {
                throw new IllegalStateException("Cannot read default avatar", e);
            }
        }
    }


    private void loadTags() {
        if (tagRepository.count() == 0) {
            List<String> tagNames = List.of(
                    "General", "Politics", "Movies", "Anime", "Travel", "Basketball", "Sports", "Fitness", "Health",
                    "Technology", "Gaming", "Music", "Food", "Fashion", "Beauty", "Photography", "Art", "Education",
                    "News", "Science", "Books", "Finance", "Business", "Entrepreneurship", "Motivation", "Memes",
                    "Nature", "Pets", "Dogs", "Cats", "Parenting", "DIY", "Crafts", "Programming", "JavaScript",
                    "Java", "Python", "Startups", "Self-Improvement", "History", "Environment", "Mental Health",
                    "Relationships", "Culture", "Spirituality", "Cars", "Motorcycles", "Space", "AI", "Podcasts"
            );

            for (String name : tagNames) {
                Tag tag = new Tag();
                tag.setName(name);
                tagRepository.save(tag);
            }
        }
    }
    private void loadComments() {
        if (commentRepository.count() == 0) {
            List<Post> posts = postRepository.findAll();
            List<String> userEmails = List.of(
                    "petar@gmail.com", "marko@gmail.com", "mirko@gmail.com", "ana.jovic@gmail.com",
                    "nikola.petrovic@gmail.com", "jelena.m@gmail.com", "uros.ivanovic@gmail.com",
                    "tijana.k@gmail.com", "stefan.nikolic@gmail.com", "milica.s@gmail.com",
                    "filip.djordjevic@gmail.com", "katarina.l@gmail.com", "dusan.radovic@gmail.com"
            );

            List<String> sampleComments = List.of(
                    "Totally agree with you!",
                    "Interesting perspective, thanks for sharing.",
                    "Haha, this made my day 😂",
                    "That looks amazing!",
                    "I’ve been thinking the same thing.",
                    "Great post, keep it up!",
                    "Where was this taken?",
                    "Can you share more about this?",
                    "Wow, love this idea!",
                    "Been there, done that 😅",
                    "Inspirational! Thanks.",
                    "I needed to hear this today.",
                    "Yesss! 100% agree.",
                    "Bookmarking this 🙌",
                    "Love this energy 🔥"
            );

            Random random = new Random();

            for (Post post : posts) {
                int numberOfComments = random.nextInt(4); // 0 to 3 comments per post

                for (int i = 0; i < numberOfComments; i++) {
                    String email = userEmails.get(random.nextInt(userEmails.size()));
                    Optional<User> optionalUser = userRepository.findByEmail(email);

                    if (optionalUser.isEmpty()) continue;

                    User user = optionalUser.get();

                    Comment comment = new Comment();
                    comment.setUser(user);
                    comment.setPost(post);

                    String content = sampleComments.get(random.nextInt(sampleComments.size()));
                    comment.setContent(content);


                    commentRepository.save(comment);
                }
            }
        }
    }

    private void loadPosts() {
        if (postRepository.count() == 0) {

            Post post1 = new Post();
            post1.setContent("Hello World!");
            post1.setCreationDate(0L);
            post1.setUser(userRepository.findByEmail("petar@gmail.com").get());

            postRepository.save(post1);

            String[] userEmails = {
                    "petar@gmail.com", "marko@gmail.com", "mirko@gmail.com", "ana.jovic@gmail.com",
                    "nikola.petrovic@gmail.com", "jelena.m@gmail.com", "uros.ivanovic@gmail.com",
                    "tijana.k@gmail.com", "stefan.nikolic@gmail.com", "milica.s@gmail.com",
                    "filip.djordjevic@gmail.com", "katarina.l@gmail.com", "dusan.radovic@gmail.com"
            };

            // Map content to related tag names
            Map<String, List<String>> contentTagMap = Map.ofEntries(
                    Map.entry("Just got back from an amazing trip!", List.of("Travel", "Photography")),
                    Map.entry("Anyone else watching this series? It's 🔥", List.of("Movies", "Entertainment")),
                    Map.entry("Working on a new project—can’t wait to share more soon.", List.of("Technology", "Startups")),
                    Map.entry("Feeling grateful today. 🙏", List.of("Self-Improvement", "Mental Health")),
                    Map.entry("Any recommendations for good books?", List.of("Books", "Education")),
                    Map.entry("New recipe turned out great! 😋", List.of("Food")),
                    Map.entry("Caught a beautiful sunset today.", List.of("Nature", "Photography")),
                    Map.entry("What’s everyone listening to lately?", List.of("Music")),
                    Map.entry("Big news coming soon!", List.of("General")),
                    Map.entry("Is it just me, or is this weather perfect?", List.of("Environment")),
                    Map.entry("Can’t stop thinking about that movie!", List.of("Movies")),
                    Map.entry("Productivity mode: ON 💻☕", List.of("Technology", "Productivity")),
                    Map.entry("Late night thoughts…", List.of("General", "Mental Health")),
                    Map.entry("Exploring the city was such a vibe today.", List.of("Travel", "Culture")),
                    Map.entry("My cat decided to help me work today 😂", List.of("Pets", "Cats")),
                    Map.entry("Basketball season is heating up! 🏀", List.of("Basketball", "Sports")),
                    Map.entry("Trying out digital painting for the first time!", List.of("Art", "Creativity")),
                    Map.entry("Let’s talk about tech trends 👇", List.of("Technology", "AI")),
                    Map.entry("So inspired by this documentary.", List.of("Education", "Movies")),
                    Map.entry("Weekend well spent 🌿", List.of("Lifestyle", "Nature"))
            );
            Map<String, Tag> tagLookup = tagRepository.findAll()
                    .stream()
                    .collect(Collectors.toMap(t -> t.getName().toLowerCase(), t -> t));

            Random random = new Random();
            List<String> contents = new ArrayList<>(contentTagMap.keySet());

            for (int i = 0; i < 30; i++) {
                String email = userEmails[random.nextInt(userEmails.length)];
                Optional<User> optionalUser = userRepository.findByEmail(email);

                if (optionalUser.isEmpty()) continue;

                User user = optionalUser.get();

                String content = contents.get(random.nextInt(contents.size()));
                List<String> tagNames = contentTagMap.get(content);

                // Get Tag objects from tag names (case-insensitive match)
                Set<Tag> postTags = tagNames.stream()
                        .map(tagName -> tagLookup.get(tagName.toLowerCase()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

                // Create hashtags from tags
                String hashtags = postTags.stream()
                        .map(tag -> "#" + tag.getName().replaceAll("\\s+", "").toLowerCase())
                        .collect(Collectors.joining(" "));

                Post post = new Post();
                post.setUser(user);
                post.setContent(content + " " + hashtags);
                post.setTags(new ArrayList<>(postTags));

                int daysAgo = random.nextInt(30); // 0 to 29 days ago
                int hour = random.nextInt(24);
                int minute = random.nextInt(60);
                int second = random.nextInt(60);

                LocalDate randomDate = LocalDate.now().minusDays(daysAgo);
                LocalDateTime timestamp = randomDate.atTime(hour, minute, second);
                post.setCreationDate(Timestamp.valueOf(timestamp).getTime());

                postRepository.save(post);
            }
        }
    }



    private void loadRoles() {
        if (roleRepository.count() == 0) {
            Role role1 = new Role();
            role1.setRoleType(RoleType.ADMIN);
            roleRepository.save(role1);

            Role role2 = new Role();
            role2.setRoleType(RoleType.USER);
            roleRepository.save(role2);

            Role role3 = new Role();
            role3.setRoleType(RoleType.PRIVATE);
            roleRepository.save(role3);

            Role role4 = new Role();
            role4.setRoleType(RoleType.PUBLIC);
            roleRepository.save(role4);
        }

    }


    private void loadUserRelationships() {

        if (userRelationshipRepository.count() == 0) {
            UserRelationship userRelationship1 = new UserRelationship();
            userRelationship1.setUser1(userRepository.findByEmail("petar@gmail.com").get());
            userRelationship1.setUser2(userRepository.findByEmail("marko@gmail.com").get());
            userRelationship1.setStatus(RelationshipStatus.CONFIRMED);
            userRelationshipRepository.save(userRelationship1);

            UserRelationship userRelationship2 = new UserRelationship();
            userRelationship2.setUser1(userRepository.findByEmail("petar@gmail.com").get());
            userRelationship2.setUser2(userRepository.findByEmail("mirko@gmail.com").get());
            userRelationship2.setStatus(RelationshipStatus.CONFIRMED);
            userRelationshipRepository.save(userRelationship2);

            UserRelationship userRelationship3 = new UserRelationship();
            userRelationship3.setUser1(userRepository.findByEmail("marko@gmail.com").get());
            userRelationship3.setUser2(userRepository.findByEmail("mirko@gmail.com").get());
            userRelationship3.setStatus(RelationshipStatus.CONFIRMED);
            userRelationshipRepository.save(userRelationship3);



        }
    }
}
