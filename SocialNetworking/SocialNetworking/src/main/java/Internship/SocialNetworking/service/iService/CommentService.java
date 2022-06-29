package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.dto.CommentInfoDTO;
import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.dto.CommentDTO;
import Internship.SocialNetworking.models.Person;

import java.util.List;

public interface CommentService {
    CommentInfoDTO addComment(CommentDTO commentDTO, Long creatorId);
    List<CommentInfoDTO> getCommentsByPostId(Long postId);
    List<CommentInfoDTO> getCommentsByCommentId(Long commentId);
    CommentInfoDTO deleteComment(Long commentId, Person loggedUser);
}
