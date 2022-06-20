package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.PostDTO;

public interface PostService {
    Post addNewPost(PostDTO postDTO);
}
