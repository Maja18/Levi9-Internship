package Internship.SocialNetworking.service;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.repository.GroupRepository;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PostServiceTests {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private NotificationServiceImpl notificationService;

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
        post.setCreationDate(LocalDateTime.now());
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
        Assertions.assertEquals(2, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size());
    }

    @Test
    void testGetAllUserNotGroupPostsIfNotFriends(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");

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
        Assertions.assertEquals(1, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size());
    }

    @Test
    void testGetAllUserGroupPostsIfNotFriends(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setGroupId(1L);

        Post postSecond = new Post();
        postSecond.setPostId(2L);
        postSecond.setDescription("mikin drugi post");
        postSecond.setCreatorId(2L);
        postSecond.setPublic(false);
        postSecond.setCreationDate(LocalDateTime.now());
        posts.add(postFirst);
        posts.add(postSecond);

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setPublic(true);
        group.setName("grupa");
        group.setCreatorId(person.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(person);
        members.add(loggedPerson);
        group.setMembers(members);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(groupRepository.findByGroupId(postFirst.getGroupId())).thenReturn(group);
        Assertions.assertFalse(postService.getAllUserPosts(person.getPersonId(), loggedPerson).isEmpty());
        Assertions.assertEquals(1, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size() );
    }

    @Test
    void testGetAllUserGroupPostsIfFriends(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setGroupId(1L);

        Post postSecond = new Post();
        postSecond.setPostId(2L);
        postSecond.setDescription("mikin drugi post");
        postSecond.setCreatorId(2L);
        postSecond.setPublic(false);
        postSecond.setCreationDate(LocalDateTime.now());
        posts.add(postFirst);
        posts.add(postSecond);

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setPublic(true);
        group.setName("grupa");
        group.setCreatorId(person.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(person);
        members.add(loggedPerson);
        group.setMembers(members);

        List<Person> friends = new ArrayList<>();
        friends.add(loggedPerson);
        person.setFriends(friends);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(groupRepository.findByGroupId(postFirst.getGroupId())).thenReturn(group);
        Assertions.assertFalse(postService.getAllUserPosts(person.getPersonId(), loggedPerson).isEmpty());
        Assertions.assertEquals(2, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size() );
    }

    @Test
    void testAddNewNotGroupPost(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        PostDTO post = new PostDTO();
        post.setDescription("my first post");
        post.setImageUrl("image url");
        post.setIsPublic(true);

        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(null);
        Assertions.assertNotNull(postService.addNewPost(post, loggedPerson));
        Assertions.assertEquals("my first post", postService.addNewPost(post, loggedPerson).getDescription());
    }

    @Test
    void testAddNewGroupPost(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setName("group");
        List<Person> members = new ArrayList<>();
        members.add(loggedPerson);
        group.setMembers(members);

        PostDTO post = new PostDTO();
        post.setDescription("my first post");
        post.setImageUrl("image url");
        post.setIsPublic(true);
        post.setGroupId(group.getGroupId());

        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(group);
        Assertions.assertNotNull(postService.addNewPost(post, loggedPerson));
        Assertions.assertEquals("my first post", postService.addNewPost(post, loggedPerson).getDescription());
        Assertions.assertEquals(1L, postService.addNewPost(post, loggedPerson).getGroupId());

    }

    @Test
    void testGetNoFriendsPostsIfAllArePassed(){
        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");
        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(2L);
        post.setPublic(false);
        post.setCreationDate(LocalDateTime.now().minusDays(3));
        posts.add(post);

        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");
        List<Person> friends = new ArrayList<>();
        friends.add(person);
        loggedPerson.setFriends(friends);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        Assertions.assertEquals(0,postService.getAllFriendPosts(loggedPerson).size());
    }
}
