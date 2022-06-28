package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.dto.HidePostDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.dto.PostInfoDTO;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;

import java.util.List;

public interface PostService {
    Post addNewPost(PostDTO postDTO, Person loggedPerson);
    List<Post> getAllUserPosts(Long userId, Person loggedPerson);
    PostInfoDTO hidePost(HidePostDTO hidePostDTO, Long personId);
    List<Post> getAllFriendPosts(Person loggedPerson);
}
