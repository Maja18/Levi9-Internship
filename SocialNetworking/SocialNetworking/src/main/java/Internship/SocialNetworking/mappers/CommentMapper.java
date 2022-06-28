package Internship.SocialNetworking.mappers;

import Internship.SocialNetworking.dto.CommentInfoDTO;
import Internship.SocialNetworking.models.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD
)
public interface CommentMapper {
    CommentInfoDTO commentToCommentInfoDTO(Comment comment);
    List<CommentInfoDTO> commentsToCommentsInfoDTOs(List<Comment> comments);
}
