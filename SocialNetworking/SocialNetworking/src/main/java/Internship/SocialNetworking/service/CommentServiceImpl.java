package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.CommentDTO;
import Internship.SocialNetworking.repository.CommentRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.iService.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    PostRepository postRepository;
    PersonRepository personRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
                              PersonRepository personRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Comment addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        boolean val = validation(commentDTO.getPostId(), commentDTO.getParentId(), commentDTO.getCreatorId());

        if(val) {
            comment.setContent(commentDTO.getContent());
            comment.setPostId(commentDTO.getPostId());
            comment.setParentId(commentDTO.getParentId());
            comment.setCreatorId(commentDTO.getCreatorId());

            return commentRepository.save(comment);
        }

        return null;
    }

    private boolean validation(Long postId, Long parentId, Long creatorId) {
        Post post = postRepository.getByPostId(postId);
        Comment comment = commentRepository.getByCommentId(parentId);
        Person person = personRepository.findByPersonId(creatorId);

        if(post != null && person != null){
            if(parentId != null && comment != null){
                return true;
            }else if(parentId == null){
                return true;
            }

            return false;
        }

        return false;
    }
}
