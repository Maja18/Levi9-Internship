package Internship.SocialNetworking.service.interface_service;

import Internship.SocialNetworking.dto.HidePostDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.dto.PostInfoDTO;
import Internship.SocialNetworking.models.Person;

import java.util.List;

public interface PostService {
    PostInfoDTO hidePost(HidePostDTO hidePostDTO, Long personId);
    PostDTO addNewPost(PostDTO postDTO, Person loggedPerson);
    List<PostDTO> getAllUserPosts(Long userId, Person loggedPerson);
    List<PostDTO> getAllFriendPosts(Person loggedPerson);
}
