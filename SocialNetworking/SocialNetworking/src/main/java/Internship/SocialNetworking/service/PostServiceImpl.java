package Internship.SocialNetworking.service;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.iService.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final GroupRepository groupRepository;
    private final  PostRepository postRepository;
    private final PersonRepository personRepository;

    @Override
    public Post addNewPost(PostDTO postDTO){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        Person loggedPerson = (Person) currentUser.getPrincipal();
        Post post = new Post();
        GroupNW group = groupRepository.findByGroupId(postDTO.getGroupId());
        if (group == null){
            post = addPostOutsideGroup(postDTO,loggedPerson);
        }else{
            post = addPostToGroup(postDTO, group, loggedPerson);
        }
        postRepository.save(post);

        return post;
    }

    public Post addPostToGroup(PostDTO postDTO, GroupNW group,Person loggedPerson) {
        List<Person> members= group.getMembers();
        Post post = new Post();
        for (Person member:members) {
            if (member.getPersonId().equals(loggedPerson.getPersonId())) {
                post.setPublic(postDTO.getIsPublic());
                post.setGroupId(postDTO.getGroupId());
                LocalDateTime currentDate = LocalDateTime.now();
                post.setCreationDate(currentDate);
                post.setCreatorId(loggedPerson.getPersonId());
                post.setDescription(postDTO.getDescription());
                post.setImageUrl(postDTO.getImageUrl());
                post.setVideoUrl(postDTO.getVideoUrl());
            }
        }

        return post;
    }

    public Post addPostOutsideGroup(PostDTO postDTO, Person loggedPerson) {
        Post post = new Post();
        post.setPublic(postDTO.getIsPublic());
        LocalDateTime currentDate = LocalDateTime.now();
        post.setCreationDate(currentDate);
        post.setCreatorId(loggedPerson.getPersonId());
        post.setDescription(postDTO.getDescription());
        post.setImageUrl(postDTO.getImageUrl());
        post.setVideoUrl(postDTO.getVideoUrl());

        return post;
    }

    @Override
    public List<Post> getAllUserPosts(Long userId) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        Person loggedPerson = (Person) currentUser.getPrincipal();
        List<Post> allPosts = postRepository.findByCreatorId(userId);
        Person person = personRepository.findByPersonId(userId);
        List<Person> personFriends = person.getFriends();
        List<Post> posts = new ArrayList<>();
        for (Post p: allPosts) {
            if (p.getGroupId() != null){
                getAllGroupPosts(loggedPerson, posts, p);
            }else{
                getAllNotGroupPosts(loggedPerson, personFriends, posts, p);
            }
        }
        return posts;
    }

    private void getAllNotGroupPosts(Person loggedPerson, List<Person> personFriends, List<Post> posts, Post p) {
        for (Person friend : personFriends) {
            System.out.println(friend.getName());
            if (friend.getPersonId().equals(loggedPerson.getPersonId())) {
                posts.add(p);
            } else if (p.isPublic()) {
                posts.add(p);
                }
            }
    }

    private void getAllGroupPosts(Person loggedPerson, List<Post> posts, Post p) {
        GroupNW group = groupRepository.findByGroupId(p.getGroupId());
        List<Person> groupMembers = group.getMembers();
        for (Person member:groupMembers) {
            if (member.getPersonId().equals(loggedPerson.getPersonId())){
                posts.add(p);
            }else if(group.isPublic()){
                posts.add(p);
            }
        }
    }
}
