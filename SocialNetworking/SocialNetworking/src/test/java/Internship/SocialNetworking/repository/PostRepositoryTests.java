package Internship.SocialNetworking.repository;
import Internship.SocialNetworking.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostRepositoryTests {

    @Resource
    private PostRepository postRepository;

    @Test
    void doesPostExitsById() {
        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("my first post");
        post.setCreationDate(LocalDateTime.now());
        post.setCreatorId(1L);
        post.setIsPublic(true);
        postRepository.save(post);
        Post p = postRepository.findByPostId(1L);
        assertThat(p.getPostId()).isEqualTo(1L);
    }

    @Test
    void doesPostNotExitsById() {
        Post p = postRepository.findByPostId(1L);
        assertThat(p.getPostId()).isNotEqualTo(3L);
    }

    @Test
    void getAllPosts(){
        List<Post> posts = postRepository.findAll();
        assertThat(posts).isNotEmpty();
    }

    @Test
    void findPostsByCreatorId(){
        List<Post> posts = postRepository.findByCreatorId(2L);
        assertThat(posts).isNotEmpty();
    }

    @Test
    void findPostsByCreatorIdNotExists(){
        List<Post> posts = postRepository.findByCreatorId(7L);
        assertThat(posts).isEmpty();
    }
}
