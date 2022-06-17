package Internship.SocialNetworking.service;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.PostDTO;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.iService.PostService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private GroupRepository groupRepository;
    private PostRepository postRepository;

    public PostServiceImpl(GroupRepository groupRepository,PostRepository postRepository){
        this.groupRepository = groupRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Post addNewPost(PostDTO postDTO){
        Post post = new Post();
        GroupNW group = groupRepository.findByGroupId(postDTO.getGroupId());
        List<Person> members= group.getMembers();
        for (Person member:members) {
            if (member.getPersonId().equals(postDTO.getUserId())) {
                post.setPublic(postDTO.getIsPublic());
                post.setGroup(group);
                LocalDateTime currentDate = LocalDateTime.now();
                post.setCreationDate(currentDate);
                post.setCreatorId(postDTO.getUserId());
                post.setDescription(postDTO.getDescription());
                post.setImageUrl(postDTO.getImageUrl());
                post.setVideoUrl(postDTO.getVideoUrl());
                }
            }
        postRepository.save(post);

        return post;
    }
}
