package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.dto.CommentDTO;
import Internship.SocialNetworking.models.Person;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentDTO commentDTO, Long creatorId);
    List<Comment> getCommentsByPostId(Long postId);
    List<Comment> getCommentsByCommentId(Long commentId);
    String deleteComment(Long commentId, Person loggedUser);
}
