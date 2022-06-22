package Internship.SocialNetworking.service;
import Internship.SocialNetworking.dto.HidePostDTO;
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
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final GroupRepository groupRepository;
    private final  PostRepository postRepository;
    private final PersonRepository personRepository;

    private final NotificationServiceImpl notificationService;

    @Override
    public Post addNewPost(PostDTO postDTO){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        Person loggedPerson = (Person) currentUser.getPrincipal();
        Post post;
        Optional<GroupNW> group = Optional.ofNullable(groupRepository.findByGroupId(postDTO.getGroupId()));
        if (group.isEmpty()){
            post = addPostOutsideGroup(postDTO,loggedPerson);
        }else{
            post = addPostToGroup(postDTO, group, loggedPerson);
            notificationService.addNotificationPost(group.get().getName(),
                    personRepository.findByEmailEquals(loggedPerson.getEmail()));
        }
        postRepository.save(post);

        return post;
    }

    public Post addPostToGroup(PostDTO postDTO, Optional<GroupNW> group, Person loggedPerson) {
        Post post = new Post();
        if (group.get().getMembers().stream().anyMatch(member -> member.getPersonId().equals(loggedPerson.getPersonId()))){
            post.setPublic(postDTO.getIsPublic());
            post.setGroupId(postDTO.getGroupId());
            LocalDateTime currentDate = LocalDateTime.now();
            post.setCreationDate(currentDate);
            post.setCreatorId(loggedPerson.getPersonId());
            post.setDescription(postDTO.getDescription());
            post.setImageUrl(postDTO.getImageUrl());
            post.setVideoUrl(postDTO.getVideoUrl());
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
    public List<Post> getAllUserPosts(Long userId, Person loggedPerson) {
        List<Post> allPosts = postRepository.findByCreatorId(userId);
        List<Post> posts = new ArrayList<>();
        allPosts.stream().forEach(p-> {
            if (p.getGroupId() != null){
                getAllGroupPosts(loggedPerson,posts, p);
            }else{
                getAllNotGroupPosts(loggedPerson,userId, posts, p);
            }
        });

        return posts;
    }

    private void getAllNotGroupPosts(Person loggedPerson,Long userId , List<Post> posts, Post p) {
        Person person = personRepository.findByPersonId(userId);
        List<Person> personFriends = person.getFriends();
        if (personFriends.isEmpty() && p.isPublic()){
            posts.add(p);
        }else {
            personFriends.stream().forEach(friend -> {
                if (friend.getPersonId().equals(loggedPerson.getPersonId()))
                    posts.add(p);
                else
                    return;
                if (p.isPublic() && !friend.getPersonId().equals(loggedPerson.getPersonId())) {
                    posts.add(p);
                }
            });
        }
    }

    private void getAllGroupPosts(Person loggedPerson,List<Post> posts, Post p) {
        GroupNW group = groupRepository.findByGroupId(p.getGroupId());
        List<Person> groupMembers = group.getMembers();
        groupMembers.stream().forEach(member -> {
            if (member.getPersonId().equals(loggedPerson.getPersonId()))
                posts.add(p);
            else
                return;
            if (group.isPublic() && ! member.getPersonId().equals(loggedPerson.getPersonId())) {
                posts.add(p);
            }
        });
    }

    @Override
    public Post hidePost(HidePostDTO hidePostDTO, Long personId) {

        if(validation(hidePostDTO, personId)) {
            Post post = postRepository.findByPostId(hidePostDTO.getPostId());
            Person blockPerson = personRepository.findByPersonId(hidePostDTO.getPersonId());
            List<Person> blockListPersons = post.getBlockedPersons();
            blockListPersons.add(blockPerson);
            post.setBlockedPersons(blockListPersons);

            return postRepository.save(post);
        }
        return null;
    }

    private boolean validation(HidePostDTO hidePostDTO, Long personId) {
        Person person = personRepository.findByPersonId(personId);
        Person blockPerson = personRepository.findByPersonId(hidePostDTO.getPersonId());
        Post post = postRepository.findByPostId(hidePostDTO.getPostId());

        if(person != null && blockPerson != null && post != null){
            GroupNW groupNW = groupRepository.findByGroupId(post.getGroupId());
            if(person.getPersonId() == post.getCreatorId() && person.getPersonId() != blockPerson.getPersonId()){
                if(groupNW.getMembers().stream().anyMatch(m -> m.getPersonId() == blockPerson.getPersonId())){
                    if(post.getBlockedPersons().stream().anyMatch(b -> b.getPersonId() == blockPerson.getPersonId())){
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
