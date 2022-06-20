package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.PostDTO;

public interface PostService {
    Post addNewPost(PostDTO postDTO);
    void addPostToGroup(PostDTO postDTO, Post post, GroupNW group);
    void addPostOutsideGroup(PostDTO postDTO, Post post);
}
