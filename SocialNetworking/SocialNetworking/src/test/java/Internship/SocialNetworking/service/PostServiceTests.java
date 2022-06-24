package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTests {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PersonRepository personRepository;

    @Before
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetFriendsPosts(){
        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");
        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(2L);
        post.setPublic(false);
        posts.add(post);

        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");
        List<Person> friends = new ArrayList<>();
        friends.add(person);
        loggedPerson.setFriends(friends);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        Assertions.assertFalse(postService.getAllFriendPosts(loggedPerson).isEmpty());
    }

    @Test
    void testGetAllUserNotGroupPostsIfFriends(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");
        List<Person> friends = new ArrayList<>();
        friends.add(loggedPerson);
        person.setFriends(friends);

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());

        Post postSecond = new Post();
        postSecond.setPostId(2L);
        postSecond.setDescription("mikin drugi post");
        postSecond.setCreatorId(2L);
        postSecond.setPublic(false);
        postSecond.setCreationDate(LocalDateTime.now());
        posts.add(postFirst);
        posts.add(postSecond);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        Assertions.assertFalse(postService.getAllUserPosts(person.getPersonId(), loggedPerson).isEmpty());
        Assertions.assertTrue(postService.getAllUserPosts(person.getPersonId(), loggedPerson).size() == 2);

    }

}
