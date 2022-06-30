package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.dto.HidePostDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.dto.PostInfoDTO;
import Internship.SocialNetworking.models.Person;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostInfoDTO hidePost(HidePostDTO hidePostDTO, Long personId);
    PostDTO addNewPost(PostDTO postDTO, Person loggedPerson);
    List<PostDTO> getAllUserPosts(Long userId, Person loggedPerson);
    List<PostDTO> getAllFriendPosts(Person loggedPerson);
    List<String> getFileNames(List<MultipartFile> multipartFiles) throws IOException;
}
