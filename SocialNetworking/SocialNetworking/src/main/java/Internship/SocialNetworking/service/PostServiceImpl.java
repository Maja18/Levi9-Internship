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
        List<GroupNW> allGroups = groupRepository.findAll();
        for (GroupNW g: allGroups) {
            List<Person> members= g.getMembers();
            GroupNW group = groupRepository.findById(g.getGroupId()).get();
            for (Person member:members) {
                if (member.getPersonId().equals(postDTO.getUserId())) {
                    post.setPublic(postDTO.getIsPublic());
                    post.setGroup(group);
                    LocalDateTime currentDate = LocalDateTime.now();
                    post.setCreationDate(currentDate);
                    post.setCreatorId(postDTO.getUserId());
                    if (postDTO.getContent() != null){
                        post.setContent(postDTO.getContent());
                    }
                    if (postDTO.getImageUrl() != null) {
                        post.setImageUrl(postDTO.getImageUrl());
                    }
                    if (postDTO.getVideoUrl() != null) {
                        post.setVideoUrl(postDTO.getVideoUrl());
                    }
                }
            }
        }
        postRepository.save(post);

        return post;
    }
}
