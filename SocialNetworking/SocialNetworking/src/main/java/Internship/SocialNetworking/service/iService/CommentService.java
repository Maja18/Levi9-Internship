package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentDTO commentDTO, Long creatorId);

    List<Comment> getCommentsByPostId(Long postId);
}
