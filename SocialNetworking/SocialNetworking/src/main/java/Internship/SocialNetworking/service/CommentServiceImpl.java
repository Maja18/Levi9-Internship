package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.CommentDTO;
import Internship.SocialNetworking.repository.CommentRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.iService.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PersonRepository personRepository;
    private final GroupRepository groupRepository;

    @Override
    public Comment addComment(CommentDTO commentDTO, Long creatorId) {
        Comment comment = new Comment();
        boolean val = validation(commentDTO, creatorId);

        if(val) {
            comment.setContent(commentDTO.getContent());
            comment.setPostId(commentDTO.getPostId());
            comment.setParentId(commentDTO.getParentId());
            comment.setCreatorId(creatorId);
            comment.setCreationDate(LocalDateTime.now());

            return commentRepository.save(comment);
        }

        return null;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        List<Comment> commentList = commentRepository.findByPostIdOrderByCreationDateDesc(postId);
        return commentList;
    }

    private boolean validation(CommentDTO commentDTO, Long creatorId) {
        Post post = postRepository.findByPostId(commentDTO.getPostId());
        Comment comment = commentRepository.findByCommentId(commentDTO.getParentId());
        Person person = personRepository.findByPersonId(creatorId);

        if(post != null && person != null){
            GroupNW group = groupRepository.findByGroupId(post.getGroupId());
            List<Person> members = group.getMembers();

            if(members.stream().anyMatch(m-> m.getPersonId() == creatorId)){
                if(comment != null && comment.getPostId() == post.getPostId()){
                    return true;
                }else if(commentDTO.getParentId() == null){
                    return true;
                }
            }
        }

        return false;
    }
}
