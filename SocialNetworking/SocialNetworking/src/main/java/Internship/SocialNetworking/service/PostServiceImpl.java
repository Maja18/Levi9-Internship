package Internship.SocialNetworking.service;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.PostDTO;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.iService.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private GroupRepository groupRepository;
    private PostRepository postRepository;
    private PersonRepository personRepository;

    public PostServiceImpl(GroupRepository groupRepository,PostRepository postRepository,PersonRepository personRepository){
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Post addNewPost(PostDTO postDTO){
        Post post = new Post();
        GroupNW group = groupRepository.findByGroupId(postDTO.getGroupId());
        if (group == null){
            addPostOutsideGroup(postDTO, post);
        }else{
            addPostToGroup(postDTO, post, group);
        }
        postRepository.save(post);

        return post;
    }

    public void addPostToGroup(PostDTO postDTO, Post post, GroupNW group) {
        List<Person> members= group.getMembers();
        for (Person member:members) {
            if (member.getPersonId().equals(postDTO.getUserId())) {
                post.setPublic(postDTO.getIsPublic());
                post.setGroupId(postDTO.getGroupId());
                LocalDateTime currentDate = LocalDateTime.now();
                post.setCreationDate(currentDate);
                post.setCreatorId(postDTO.getUserId());
                post.setDescription(postDTO.getDescription());
                post.setImageUrl(postDTO.getImageUrl());
                post.setVideoUrl(postDTO.getVideoUrl());
            }
        }
    }

    public void addPostOutsideGroup(PostDTO postDTO, Post post) {
        post.setPublic(postDTO.getIsPublic());
        LocalDateTime currentDate = LocalDateTime.now();
        post.setCreationDate(currentDate);
        post.setCreatorId(postDTO.getUserId());
        post.setDescription(postDTO.getDescription());
        post.setImageUrl(postDTO.getImageUrl());
        post.setVideoUrl(postDTO.getVideoUrl());
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
