package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.dto.HidePostDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;

import java.util.List;

public interface PostService {
    PostDTO addNewPost(PostDTO postDTO, Person loggedPerson);
    List<PostDTO> getAllUserPosts(Long userId, Person loggedPerson);
    Post hidePost(HidePostDTO hidePostDTO, Long personId);
    List<PostDTO> getAllFriendPosts(Person loggedPerson);
}
