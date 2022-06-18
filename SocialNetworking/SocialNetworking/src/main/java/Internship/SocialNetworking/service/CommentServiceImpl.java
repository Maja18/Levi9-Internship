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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PersonRepository personRepository;
    private final GroupRepository groupRepository;

    @Override
    public Comment addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        boolean val = validation(commentDTO);

        if(val) {
            comment.setContent(commentDTO.getContent());
            comment.setPostId(commentDTO.getPostId());
            comment.setParentId(commentDTO.getParentId());
            comment.setCreatorId(commentDTO.getCreatorId());

            return commentRepository.save(comment);
        }

        return null;
    }

    private boolean validation(CommentDTO commentDTO) {
        Post post = postRepository.findByPostId(commentDTO.getPostId());
        Comment comment = commentRepository.findByCommentId(commentDTO.getParentId());
        Person person = personRepository.findByPersonId(commentDTO.getCreatorId());

        if(post != null && person != null){
            GroupNW group = groupRepository.findByGroupId(post.getGroupId());
            List<Person> members = group.getMembers();

            for (Person p: members){
                if(p.getPersonId() == commentDTO.getCreatorId()){
                    if(commentDTO.getParentId() != null && comment != null){
                        return true;
                    }else if(commentDTO.getParentId() == null){
                        return true;
                    }
                }
            }

            return false;
        }

        return false;
    }
}
