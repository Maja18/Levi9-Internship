package Internship.SocialNetworking.service;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.exceptions.GroupException;
import Internship.SocialNetworking.exceptions.PersonException;
import Internship.SocialNetworking.mappers.PostMapper;
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
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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

    @Spy
    PostMapper postMapper = Mappers.getMapper(PostMapper.class);

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
        post.setIsPublic(false);
        post.setCreationDate(LocalDateTime.now());
        post.setIsOver(false);

        Post postSecond = new Post();
        postSecond.setPostId(1L);
        postSecond.setDescription("mikin drugi post");
        postSecond.setCreatorId(2L);
        postSecond.setIsPublic(true);
        postSecond.setCreationDate(LocalDateTime.now().minusDays(4));
        posts.add(post);
        posts.add(postSecond);

        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");
        List<Person> friends = new ArrayList<>();
        friends.add(person);
        loggedPerson.setFriends(friends);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        Assertions.assertFalse(postService.getAllFriendPosts(loggedPerson).isEmpty());
        Assertions.assertEquals(1, postService.getAllFriendPosts(loggedPerson).size());
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
        postFirst.setIsPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setIsOver(false);

        Post postSecond = new Post();
        postSecond.setPostId(2L);
        postSecond.setDescription("mikin drugi post");
        postSecond.setCreatorId(2L);
        postSecond.setIsPublic(false);
        postSecond.setCreationDate(LocalDateTime.now().minusDays(1));
        postSecond.setIsOver(false);
        posts.add(postFirst);
        posts.add(postSecond);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        Assertions.assertFalse(postService.getAllUserPosts(person.getPersonId(), loggedPerson).isEmpty());
        Assertions.assertEquals(1, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size());
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
        postFirst.setIsPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setIsOver(false);

        Post postSecond = new Post();
        postSecond.setPostId(2L);
        postSecond.setDescription("mikin drugi post");
        postSecond.setCreatorId(2L);
        postSecond.setIsPublic(false);
        postSecond.setCreationDate(LocalDateTime.now());
        postSecond.setIsOver(false);
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

        Person p = new Person();
        p.setPersonId(3L);
        p.setName("Zika");

        List<Post> posts = new ArrayList<>();
        List<Person> friends = new ArrayList<>();
        friends.add(p);
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");
        person.setFriends(friends);

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setIsPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setIsOver(false);

        Post postSecond = new Post();
        postSecond.setPostId(2L);
        postSecond.setDescription("mikin drugi post");
        postSecond.setCreatorId(2L);
        postSecond.setIsPublic(false);
        postSecond.setCreationDate(LocalDateTime.now());
        postSecond.setIsOver(false);
        posts.add(postFirst);
        posts.add(postSecond);

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
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
        postFirst.setIsPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setGroupId(1L);
        postFirst.setIsOver(false);

        Post postSecond = new Post();
        postSecond.setPostId(2L);
        postSecond.setDescription("mikin drugi post");
        postSecond.setCreatorId(2L);
        postSecond.setIsPublic(false);
        postSecond.setCreationDate(LocalDateTime.now());
        postSecond.setIsOver(false);
        posts.add(postFirst);
        posts.add(postSecond);

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
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
        post.setImageUrl("slika1.png");
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
        post.setImageUrl("slika1.png");
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
        post.setIsPublic(false);
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

    @Test
    void testGetNoUserGroupPostsIfGroupDoesNotExist(){
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
        postFirst.setIsPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setGroupId(15L);
        postFirst.setIsOver(false);
        posts.add(postFirst);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        GroupException exception = Assertions.assertThrows(GroupException.class, () -> {
            postService.getAllUserPosts(2L, loggedPerson);

        });
        Assertions.assertEquals("Group with given id doesn't exist", exception.getMessage());
    }

    @Test
    void testGetNoUserGroupPostsIfNotMemberAndGroupIsPrivate(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(false);
        group.setName("grupa");
        group.setCreatorId(person.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(person);
        group.setMembers(members);

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setIsPublic(false);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setGroupId(1L);
        postFirst.setIsOver(false);
        postFirst.setGroupId(group.getGroupId());
        posts.add(postFirst);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(groupRepository.findByGroupId(postFirst.getGroupId())).thenReturn(group);
        Assertions.assertTrue(postService.getAllUserPosts(person.getPersonId(), loggedPerson).isEmpty());
        Assertions.assertEquals(0, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size() );
    }

    @Test
    void testGetUserGroupPostsIfNotMemberAndGroupIsPublic(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
        group.setName("grupa");
        group.setCreatorId(person.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(person);
        group.setMembers(members);

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setIsPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setGroupId(1L);
        postFirst.setIsOver(false);
        postFirst.setGroupId(group.getGroupId());
        posts.add(postFirst);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(groupRepository.findByGroupId(postFirst.getGroupId())).thenReturn(group);
        Assertions.assertFalse(postService.getAllUserPosts(person.getPersonId(), loggedPerson).isEmpty());
        Assertions.assertEquals(1, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size() );
    }

    @Test
    void testGetUserGroupPostsIfMemberAndGroupIsPrivate(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(false);
        group.setName("grupa");
        group.setCreatorId(person.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(person);
        members.add(loggedPerson);
        group.setMembers(members);

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setIsPublic(false);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setGroupId(1L);
        postFirst.setIsOver(false);
        postFirst.setGroupId(group.getGroupId());
        posts.add(postFirst);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(groupRepository.findByGroupId(postFirst.getGroupId())).thenReturn(group);
        Assertions.assertFalse(postService.getAllUserPosts(person.getPersonId(), loggedPerson).isEmpty());
        Assertions.assertEquals(1, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size() );
    }

    @Test
    void testGetNoPostsIfUserIsBlocked(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();
        List<Person> blockedPersons = new ArrayList<>();
        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Mika");
        blockedPersons.add(loggedPerson);

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setIsPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setIsOver(false);
        postFirst.setBlockedPersons(blockedPersons);
        posts.add(postFirst);

        when(postRepository.findByCreatorId(person.getPersonId())).thenReturn(posts);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        Assertions.assertTrue(postService.getAllUserPosts(person.getPersonId(), loggedPerson).isEmpty());
        Assertions.assertEquals(0, postService.getAllUserPosts(person.getPersonId(), loggedPerson).size());
    }

    @Test
    void testAddNoNewGroupPostIfNotMember(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setName("group");
        List<Person> members = new ArrayList<>();
        group.setMembers(members);

        PostDTO post = new PostDTO();
        post.setDescription("my first post");
        post.setImageUrl("image url");
        post.setIsPublic(true);
        post.setGroupId(group.getGroupId());

        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(group);
        GroupException exception = Assertions.assertThrows(GroupException.class, () -> {
            postService.addNewPost(post, loggedPerson);

        });
        Assertions.assertEquals("You are not member of this group!", exception.getMessage());

    }

    @Test
    void testGetNoUserPostsIfUserDoesNotExist(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        List<Post> posts = new ArrayList<>();

        Post postFirst = new Post();
        postFirst.setPostId(1L);
        postFirst.setDescription("mikin post");
        postFirst.setCreatorId(2L);
        postFirst.setIsPublic(true);
        postFirst.setCreationDate(LocalDateTime.now());
        postFirst.setIsOver(false);
        posts.add(postFirst);

        when(postRepository.findByCreatorId(10L)).thenReturn(posts);
        PersonException exception = Assertions.assertThrows(PersonException.class, () -> {
            postService.getAllUserPosts(10L, loggedPerson);

        });
        Assertions.assertEquals("Person with given id doesn't exist", exception.getMessage());
    }
}
