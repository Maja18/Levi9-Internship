package Internship.SocialNetworking.mappers;

import Internship.SocialNetworking.dto.PostInfoDTO;
import Internship.SocialNetworking.models.Post;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD
)
public interface PostMapper {
    PostInfoDTO postToPostInfoDTO(Post post);
}
