package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.CommentInfoDTO;
import Internship.SocialNetworking.mappers.CommentMapper;
import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.dto.CommentDTO;
import Internship.SocialNetworking.repository.CommentRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.interface_service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PersonRepository personRepository;
    private final GroupRepository groupRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentInfoDTO addComment(CommentDTO commentDTO, Long creatorId) {
        Comment comment = new Comment();
        boolean val = validation(commentDTO, creatorId);

        if(val) {
            Comment parentComment = commentRepository.findByCommentId(commentDTO.getParentId());

            comment.setContent(commentDTO.getContent());
            comment.setPostId(commentDTO.getPostId());
            comment.setParentId(commentDTO.getParentId());
            comment.setCreatorId(creatorId);
            comment.setCreationDate(LocalDateTime.now());
            comment.setComments(null);

            if(parentComment != null) {
                List<Comment> commentList = parentComment.getComments();
                commentList.add(comment);
                parentComment.setComments(commentList);
                commentRepository.save(parentComment);
                return commentMapper.commentToCommentInfoDTO(parentComment);
            }

            commentRepository.save(comment);
            return commentMapper.commentToCommentInfoDTO(comment);
        }

        log.info("The comment didn't pass a validation!");
        return null;
    }

    @Override
    public List<CommentInfoDTO> getCommentsByPostId(Long postId) {
        List<Comment> commentList = commentRepository.findByPostIdOrderByCreationDateDesc(postId);
        List<Comment> commentList1 = new ArrayList<>();
        
        if(!commentList.isEmpty()) {
            for (Comment comment : commentList) {
                if(comment.getParentId() == null){
                    commentList1.add(comment);
                }
            }
        }

        return commentMapper.commentsToCommentsInfoDTOs(commentList1);
    }

    @Override
    public List<CommentInfoDTO> getCommentsByCommentId(Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        List<Comment> comments = commentRepository.findAll();
        List<Comment> childComments = new ArrayList<>();

        if(comment != null){
            comments.forEach(c->{
                if(c.getParentId() == comment.getCommentId()){
                    childComments.add(c);
                }
            });
        }

        return commentMapper.commentsToCommentsInfoDTOs(childComments);
    }

    @Override
    public CommentInfoDTO deleteComment(Long commentId, Person loggedUser) {
        Comment comment = commentRepository.findByCommentId(commentId);
        List<Comment> comments = commentRepository.findAll();

        if(comment != null && comment.getCreatorId().equals(loggedUser.getPersonId())){
            comments.forEach(c-> {
                if(c.getCommentId().equals(comment.getParentId())){
                    List<Comment> updateList = c.getComments();
                    updateList.remove(comment);
                    c.setComments(updateList);
                    commentRepository.save(c);
                }
            });

            commentRepository.delete(comment);
            return commentMapper.commentToCommentInfoDTO(comment);
        }

        log.info("The comment doesn't exist or logged user isn't a creator of the comment!");
        return null;
    }

    private boolean validation(CommentDTO commentDTO, Long creatorId) {
        Post post = postRepository.findByPostId(commentDTO.getPostId());
        Comment comment = commentRepository.findByCommentId(commentDTO.getParentId());
        Person person = personRepository.findByPersonId(creatorId);

        if(post != null && person != null && !post.getIsOver()){
            GroupNW group = groupRepository.findByGroupId(post.getGroupId());

            if(group != null) {
                List<Person> members = group.getMembers();

                if (members.stream().anyMatch(m -> m.getPersonId().equals(creatorId))) {
                    return comment != null && comment.getPostId().equals(post.getPostId()) || commentDTO.getParentId() == null;
                }
            }else if(person.getFriends().stream().anyMatch(f-> f.getPersonId().equals(post.getCreatorId()))
                    && comment != null && comment.getPostId().equals(post.getPostId())) {
                return true;

            }else if(person.getPersonId().equals(post.getCreatorId())){
                return true;

            }else return person.getFriends().stream().anyMatch(f -> f.getPersonId().equals(post.getCreatorId()))
                    && commentDTO.getParentId() == null;
        }

        return false;
    }
}
