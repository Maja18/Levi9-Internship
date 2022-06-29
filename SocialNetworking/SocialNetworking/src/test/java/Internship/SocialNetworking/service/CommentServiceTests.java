package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.CommentDTO;
import Internship.SocialNetworking.mappers.CommentMapper;
import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.repository.CommentRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class CommentServiceTests {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private CommentRepository commentRepository;

    @Spy
    CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Before
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCommentToPost(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
        group.setName("grupa");
        group.setCreatorId(loggedPerson.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(loggedPerson);
        group.setMembers(members);

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(group.getGroupId());
        post.setIsOver(false);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(post.getPostId());
        commentDTO.setContent("Komentar");
        commentDTO.setParentId(null);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(postRepository.findByPostId(commentDTO.getPostId())).thenReturn(post);
        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(group);
        when(commentRepository.findByCommentId(commentDTO.getParentId())).thenReturn(null);
        Assertions.assertNotNull(commentService.addComment(commentDTO, loggedPerson.getPersonId()));
    }

    @Test
    void addChildCommentToParentComment(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
        group.setName("grupa");
        group.setCreatorId(loggedPerson.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(loggedPerson);
        group.setMembers(members);

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(group.getGroupId());
        post.setIsOver(false);

        Comment parentComment = new Comment();
        parentComment.setCommentId(1L);
        parentComment.setParentId(null);
        parentComment.setPostId(post.getPostId());

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(post.getPostId());
        commentDTO.setContent("Komentar");
        commentDTO.setParentId(parentComment.getCommentId());

        Comment comment = new Comment();
        comment.setPostId(commentDTO.getPostId());
        comment.setParentId(commentDTO.getParentId());

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        parentComment.setComments(commentList);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(postRepository.findByPostId(commentDTO.getPostId())).thenReturn(post);
        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(group);
        when(commentRepository.findByCommentId(commentDTO.getParentId())).thenReturn(parentComment);
        Assertions.assertNotNull(commentService.addComment(commentDTO, loggedPerson.getPersonId()));
    }

    @Test
    void addCommentToPostWhoIsOver(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        GroupNW group = new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
        group.setName("grupa");
        group.setCreatorId(loggedPerson.getPersonId());
        List<Person> members = new ArrayList<>();
        members.add(loggedPerson);
        group.setMembers(members);

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(group.getGroupId());
        post.setIsOver(true);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(post.getPostId());
        commentDTO.setContent("Komentar");
        commentDTO.setParentId(null);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(postRepository.findByPostId(commentDTO.getPostId())).thenReturn(post);
        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(group);
        when(commentRepository.findByCommentId(commentDTO.getParentId())).thenReturn(null);
        Assertions.assertNull(commentService.addComment(commentDTO, loggedPerson.getPersonId()));
    }

    @Test
    void addCommentToPostWithoutGroup(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");
        List<Person> personList = new ArrayList<>();
        loggedPerson.setFriends(personList);

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(loggedPerson.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(null);
        post.setIsOver(false);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(post.getPostId());
        commentDTO.setContent("Komentar");
        commentDTO.setParentId(null);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(postRepository.findByPostId(commentDTO.getPostId())).thenReturn(post);
        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(null);
        when(commentRepository.findByCommentId(commentDTO.getParentId())).thenReturn(null);
        Assertions.assertNotNull(commentService.addComment(commentDTO, loggedPerson.getPersonId()));
    }

    @Test
    void addCommentToPostWithoutGroup1(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Ivan");

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        loggedPerson.setFriends(personList);

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(person.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(null);
        post.setIsOver(false);

        Comment parentComment = new Comment();
        parentComment.setCommentId(1L);
        parentComment.setParentId(null);
        parentComment.setPostId(post.getPostId());

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(post.getPostId());
        commentDTO.setContent("Komentar");
        commentDTO.setParentId(parentComment.getParentId());

        Comment comment = new Comment();
        comment.setPostId(commentDTO.getPostId());
        comment.setParentId(commentDTO.getParentId());

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        parentComment.setComments(commentList);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(postRepository.findByPostId(commentDTO.getPostId())).thenReturn(post);
        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(null);
        when(commentRepository.findByCommentId(commentDTO.getParentId())).thenReturn(parentComment);
        Assertions.assertNotNull(commentService.addComment(commentDTO, loggedPerson.getPersonId()));
    }

    @Test
    void addCommentToPostWithoutGroup2(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person person = new Person();
        person.setPersonId(2L);
        person.setName("Ivan");

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        loggedPerson.setFriends(personList);

        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(person.getPersonId());
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(null);
        post.setIsOver(false);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPostId(post.getPostId());
        commentDTO.setContent("Komentar");
        commentDTO.setParentId(null);

        Comment comment = new Comment();
        comment.setPostId(commentDTO.getPostId());
        comment.setParentId(commentDTO.getParentId());

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(postRepository.findByPostId(commentDTO.getPostId())).thenReturn(post);
        when(groupRepository.findByGroupId(post.getGroupId())).thenReturn(null);
        when(commentRepository.findByCommentId(commentDTO.getParentId())).thenReturn(null);
        Assertions.assertNotNull(commentService.addComment(commentDTO, loggedPerson.getPersonId()));
    }

    @Test
    void getCommentsByPostId(){
        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(1L);
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(null);
        post.setIsOver(false);

        Comment comment = new Comment();
        comment.setCommentId(1L);
        comment.setPostId(post.getPostId());
        comment.setParentId(null);

        Comment comment1 = new Comment();
        comment1.setCommentId(2L);
        comment1.setPostId(post.getPostId());
        comment1.setParentId(null);

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        commentList.add(comment1);

        when(commentRepository.findByPostIdOrderByCreationDateDesc(post.getPostId())).thenReturn(commentList);
        Assertions.assertNotNull(commentService.getCommentsByPostId(post.getPostId()));
    }

    @Test
    void getCommentsByCommentId(){
        Post post = new Post();
        post.setPostId(1L);
        post.setDescription("mikin post");
        post.setCreatorId(1L);
        post.setIsPublic(true);
        post.setCreationDate(LocalDateTime.now());
        post.setGroupId(null);
        post.setIsOver(false);

        Comment comment = new Comment();
        comment.setCommentId(1L);
        comment.setPostId(post.getPostId());
        comment.setParentId(null);

        Comment comment1 = new Comment();
        comment1.setCommentId(2L);
        comment1.setPostId(post.getPostId());
        comment1.setParentId(comment.getParentId());

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        commentList.add(comment1);

        when(commentRepository.findByCommentId(comment.getCommentId())).thenReturn(comment);
        when(commentRepository.findAll()).thenReturn(commentList);
        Assertions.assertNotNull(commentService.getCommentsByCommentId(comment.getCommentId()));
    }

    @Test
    void deleteComment(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Comment comment = new Comment();
        comment.setCreatorId(loggedPerson.getPersonId());
        comment.setCommentId(1L);
        comment.setPostId(1L);
        comment.setParentId(null);

        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);

        when(commentRepository.findByCommentId(comment.getCommentId())).thenReturn(comment);
        when(commentRepository.findAll()).thenReturn(commentList);
        Assertions.assertNotNull(commentService.deleteComment(comment.getCommentId(), loggedPerson));
    }
}
