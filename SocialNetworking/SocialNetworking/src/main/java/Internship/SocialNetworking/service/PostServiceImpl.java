package Internship.SocialNetworking.service;
import Internship.SocialNetworking.dto.HidePostDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.dto.PostInfoDTO;
import Internship.SocialNetworking.exceptions.GroupException;
import Internship.SocialNetworking.exceptions.PersonException;
import Internship.SocialNetworking.mappers.PostMapper;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.iService.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final GroupRepository groupRepository;
    private final  PostRepository postRepository;
    private final PersonRepository personRepository;
    private final NotificationServiceImpl notificationService;
    private final PostMapper postMapper;

    @Override
    public PostDTO addNewPost(PostDTO postDTO, Person loggedPerson){
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

        return postMapper.postToPostDTO(post);
    }

    public Post addPostToGroup(PostDTO postDTO, Optional<GroupNW> group, Person loggedPerson) {
        Post post = new Post();
        if (group.isEmpty())
            throw new GroupException("Group doesn't exist");
        if (group.get().getMembers().stream().anyMatch(member -> member.getPersonId().equals(loggedPerson.getPersonId()))){
            post.setIsPublic(postDTO.getIsPublic());
            post.setGroupId(postDTO.getGroupId());
            LocalDateTime currentDate = LocalDateTime.now();
            post.setCreationDate(currentDate);
            post.setCreatorId(loggedPerson.getPersonId());
            post.setDescription(postDTO.getDescription());
            post.setImageUrl(postDTO.getImageUrl());
            post.setVideoUrl(postDTO.getVideoUrl());
            post.setIsOver(false);
        } else
            throw new GroupException("You are not member of this group!");

        return post;
    }

    public Post addPostOutsideGroup(PostDTO postDTO, Person loggedPerson) {
        Post post = new Post();
        post.setIsPublic(postDTO.getIsPublic());
        LocalDateTime currentDate = LocalDateTime.now();
        post.setCreationDate(currentDate);
        post.setCreatorId(loggedPerson.getPersonId());
        post.setDescription(postDTO.getDescription());
        post.setImageUrl(postDTO.getImageUrl());
        post.setVideoUrl(postDTO.getVideoUrl());
        post.setIsOver(false);

        return post;
    }

    @Override
    public List<PostDTO> getAllUserPosts(Long userId, Person loggedPerson) {
        List<Post> allPosts = postRepository.findByCreatorId(userId);
        List<Post> posts = new ArrayList<>();
        allPosts.stream().forEach(p-> {
            if(p.getCreationDate().isBefore(LocalDateTime.now().minusDays(1)))
                p.setIsOver(true);
            if (p.getGroupId() != null)
                setAllGroupPosts(loggedPerson, posts, p);
            else
                setAllNotGroupPosts(loggedPerson,userId, posts, p);
        });

        return postMapper.postsToPostDTOs(posts);
    }

    private void setAllNotGroupPosts(Person loggedPerson,Long userId , List<Post> posts, Post p) {
        Person person = personRepository.findByPersonId(userId);
        if (person == null)
            throw new PersonException(userId, "Person with given id doesn't exist");
        List<Person> personFriends = person.getFriends();
        boolean isNullOrEmpty = ObjectUtils.isEmpty(personFriends);
        if (isNullOrEmpty && !p.getIsPublic())
            return;
        if (isNullOrEmpty && p.getIsPublic())
            posts.add(p);
        else {
            personFriends.stream().forEach(friend -> {
                if (friend.getPersonId().equals(loggedPerson.getPersonId()) && !p.getIsOver())
                    posts.add(p);
                else
                    return;
                if (p.getIsPublic() && !friend.getPersonId().equals(loggedPerson.getPersonId()) && !p.getIsOver())
                        posts.add(p);
            });
        }

        removeBlockedPosts(loggedPerson, posts, p);
    }

    private void setAllGroupPosts(Person loggedPerson,List<Post> posts, Post p) {
        GroupNW group = groupRepository.findByGroupId(p.getGroupId());
        if (group == null)
            throw new GroupException(p.getGroupId(), "Group with given id doesn't exist");
        List<Person> groupMembers = group.getMembers();
        if (!p.getIsOver()) {
            if (group.getIsPublic())
                posts.add(p);
            else {
                groupMembers.stream().forEach(member -> {
                    if (member.getPersonId().equals(loggedPerson.getPersonId()))
                        posts.add(p);
                });
            }

            removeBlockedPosts(loggedPerson, posts, p);
        }
    }

    private void removeBlockedPosts(Person loggedPerson, List<Post> posts, Post p) {
        List<Person> blockedPersons = p.getBlockedPersons();
        boolean isNullOrEmpty = ObjectUtils.isEmpty(blockedPersons);
        if(!isNullOrEmpty) {
            blockedPersons.stream().forEach(blockedUser -> {
                if (blockedUser.getPersonId().equals(loggedPerson.getPersonId()))
                    posts.remove(p);
            });
        }
    }

    @Override
    public PostInfoDTO hidePost(HidePostDTO hidePostDTO, Long personId) {

        if(validation(hidePostDTO, personId)) {
            Post post = postRepository.findByPostId(hidePostDTO.getPostId());
            Person blockPerson = personRepository.findByPersonId(hidePostDTO.getPersonId());
            List<Person> blockListPersons = post.getBlockedPersons();
            blockListPersons.add(blockPerson);
            post.setBlockedPersons(blockListPersons);
            postRepository.save(post);

            return postMapper.postToPostInfoDTO(post);
        }

        log.info("The HidePostDTO and the personId didn't pass validation!");
        return null;
    }

    private boolean validation(HidePostDTO hidePostDTO, Long personId) {
        Person person = personRepository.findByPersonId(personId);
        Person blockPerson = personRepository.findByPersonId(hidePostDTO.getPersonId());
        Post post = postRepository.findByPostId(hidePostDTO.getPostId());

        if(person != null && blockPerson != null && post != null
                && person.getPersonId().equals(post.getCreatorId())
                && !post.getIsOver()){

            GroupNW groupNW = groupRepository.findByGroupId(post.getGroupId());

            if(!Objects.equals(person.getPersonId(), blockPerson.getPersonId()) && groupNW != null
                    && groupNW.getMembers().stream().anyMatch(m -> m.getPersonId().equals(blockPerson.getPersonId()))){

                return post.getBlockedPersons().stream().noneMatch(b -> b.getPersonId().equals(blockPerson.getPersonId()));

            }else return post.getGroupId() == null
                    && person.getFriends().stream().anyMatch(f -> f.getPersonId().equals(blockPerson.getPersonId()))
                    && post.getBlockedPersons().stream().noneMatch(b -> b.getPersonId().equals(blockPerson.getPersonId()));
        }

        return false;
    }

    @Override
    public List<PostDTO> getAllFriendPosts(Person loggedPerson) {
        List<Post> friendPosts= new ArrayList<>();
        List<Person> personFriends = loggedPerson.getFriends();
        personFriends.stream().forEach(friend -> {
            List<Post> posts = postRepository.findByCreatorId(friend.getPersonId());
            for (Post p: posts) {
                if(p.getCreationDate().isBefore(LocalDateTime.now().minusDays(1)))
                    p.setIsOver(true);
                if (!p.getIsOver())
                    friendPosts.add(p);
                removeBlockedPosts(loggedPerson, friendPosts, p);
            }
        });

        return postMapper.postsToPostDTOs(friendPosts);
    }
}
