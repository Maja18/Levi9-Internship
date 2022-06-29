package Internship.SocialNetworking.mappers;

import Internship.SocialNetworking.dto.PostInfoDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.models.Post;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD
)
public interface PostMapper {
    PostInfoDTO postToPostInfoDTO(Post post);
    PostDTO postToPostDTO(Post post);
    List<PostDTO> postsToPostDTOs(List<Post> posts);

}
