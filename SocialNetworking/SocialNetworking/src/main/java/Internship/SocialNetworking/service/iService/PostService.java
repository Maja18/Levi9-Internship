package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.PostDTO;

import java.util.List;

public interface PostService {
    Post addNewPost(PostDTO postDTO);
    List<Post> getAllUserPosts(Long userId);
}
