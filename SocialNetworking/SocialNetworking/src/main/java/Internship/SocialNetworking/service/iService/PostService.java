package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.models.Post;

import java.util.List;

public interface PostService {
    Post addNewPost(PostDTO postDTO);
    List<Post> getAllUserPosts(Long userId);
}
