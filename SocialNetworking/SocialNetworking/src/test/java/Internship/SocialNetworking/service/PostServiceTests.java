package Internship.SocialNetworking.service;
import Internship.SocialNetworking.dto.HidePostDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.dto.PostInfoDTO;
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
    void blockPersonDoesNotExistInBlockList(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Stefan");
        List<Person> blockPersons = new ArrayList<>();

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(1L);
        post.setIsOver(false);
        post.setBlockedPersons(blockPersons);

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
        group.setName("grupa");
        group.setCreatorId(loggedPerson.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(person);
        members.add(loggedPerson);
        group.setMembers(members);

        HidePostDTO hidePostDTO = new HidePostDTO();
        hidePostDTO.setPostId(post.getPostId());
        hidePostDTO.setPersonId(person.getPersonId());

        PostInfoDTO postInfoDTO = new PostInfoDTO();
        postInfoDTO = postMapper.postToPostInfoDTO(post);
        System.out.println(postInfoDTO.getDescription());

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(postRepository.findByPostId(hidePostDTO.getPostId())).thenReturn(post);
        when(personRepository.findByPersonId(hidePostDTO.getPersonId())).thenReturn(person);
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(group);
        Assertions.assertNotNull(postService.hidePost(hidePostDTO, loggedPerson.getPersonId()));
    }

    @Test
    void blockPersonExistInBlockList(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");

        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Stefan");
        List<Person> blockPersons = new ArrayList<>();

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(1L);
        post.setIsOver(false);
        blockPersons.add(person);
        post.setBlockedPersons(blockPersons);

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
        group.setName("grupa");
        group.setCreatorId(loggedPerson.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(person);
        members.add(loggedPerson);
        group.setMembers(members);

        HidePostDTO hidePostDTO = new HidePostDTO();
        hidePostDTO.setPostId(post.getPostId());
        hidePostDTO.setPersonId(person.getPersonId());

        PostInfoDTO postInfoDTO = new PostInfoDTO();
        postInfoDTO = postMapper.postToPostInfoDTO(post);
        System.out.println(postInfoDTO.getDescription());

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(postRepository.findByPostId(hidePostDTO.getPostId())).thenReturn(post);
        when(personRepository.findByPersonId(hidePostDTO.getPersonId())).thenReturn(person);
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(group);
        Assertions.assertNull(postService.hidePost(hidePostDTO, loggedPerson.getPersonId()));
    }

    @Test
    void groupIsNullForPostAndBlockPersonDoesNotFriendWithLoggedPerson(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");
        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);

        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Stefan");
        List<Person> blockPersons = new ArrayList<>();

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(null);
        post.setIsOver(false);
        post.setBlockedPersons(blockPersons);

        HidePostDTO hidePostDTO = new HidePostDTO();
        hidePostDTO.setPostId(post.getPostId());
        hidePostDTO.setPersonId(person.getPersonId());

        PostInfoDTO postInfoDTO = new PostInfoDTO();
        postInfoDTO = postMapper.postToPostInfoDTO(post);
        System.out.println(postInfoDTO.getDescription());

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(postRepository.findByPostId(hidePostDTO.getPostId())).thenReturn(post);
        when(personRepository.findByPersonId(hidePostDTO.getPersonId())).thenReturn(person);
        Assertions.assertNull(postService.hidePost(hidePostDTO, loggedPerson.getPersonId()));
    }

    @Test
    void HidePostWherePostIsOver(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");
        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);

        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Stefan");
        List<Person> blockPersons = new ArrayList<>();

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(null);
        post.setIsOver(true);
        post.setBlockedPersons(blockPersons);

        HidePostDTO hidePostDTO = new HidePostDTO();
        hidePostDTO.setPostId(post.getPostId());
        hidePostDTO.setPersonId(person.getPersonId());

        PostInfoDTO postInfoDTO = new PostInfoDTO();
        postInfoDTO = postMapper.postToPostInfoDTO(post);
        System.out.println(postInfoDTO.getDescription());

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(postRepository.findByPostId(hidePostDTO.getPostId())).thenReturn(post);
        when(personRepository.findByPersonId(hidePostDTO.getPersonId())).thenReturn(person);
        Assertions.assertNull(postService.hidePost(hidePostDTO, loggedPerson.getPersonId()));
    }

    @Test
    void groupIsNullForPostAndBlockPersonIsFriendWithLoggedPerson(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Pera");
        List<Person> friendList = new ArrayList<>();

        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Stefan");
        List<Person> blockPersons = new ArrayList<>();

        friendList.add(person);
        loggedPerson.setFriends(friendList);

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(null);
        post.setIsOver(false);
        post.setBlockedPersons(blockPersons);

        HidePostDTO hidePostDTO = new HidePostDTO();
        hidePostDTO.setPostId(post.getPostId());
        hidePostDTO.setPersonId(person.getPersonId());

        PostInfoDTO postInfoDTO = new PostInfoDTO();
        postInfoDTO = postMapper.postToPostInfoDTO(post);
        System.out.println(postInfoDTO.getDescription());

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);
        when(postRepository.findByPostId(hidePostDTO.getPostId())).thenReturn(post);
        when(personRepository.findByPersonId(hidePostDTO.getPersonId())).thenReturn(person);
        Assertions.assertNotNull(postService.hidePost(hidePostDTO, loggedPerson.getPersonId()));
    }
}
