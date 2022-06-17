package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.dto.CommentDTO;

public interface CommentService {
    Comment addComment(CommentDTO commentDTO);
}
