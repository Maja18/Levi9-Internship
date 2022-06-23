package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.dto.CommentDTO;
import Internship.SocialNetworking.service.CommentServiceImpl;
import Internship.SocialNetworking.service.PersonServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/comment")
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class CommentController {
    private final CommentServiceImpl commentService;
    private final PersonServiceImpl personService;

    @PostMapping
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person loggedUser = personService.findByPersonId(currentUser.getPersonId());

        Comment comment = commentService.addComment(commentDTO, loggedUser.getPersonId());

        if (comment == null) {
            return new ResponseEntity<Comment>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    @GetMapping(value = "/post/{post-id}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<Comment>> showComments(@PathVariable(name = "post-id") Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);

        if (comments == null) {
            return new ResponseEntity<List<Comment>>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/{comment-id}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<Comment>> showCommentsByCommentId(@PathVariable(name = "comment-id") Long commentId) {
        List<Comment> comments = commentService.getCommentsByCommentId(commentId);

        if (comments == null) {
            return new ResponseEntity<List<Comment>>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{comment-id}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "comment-id") Long commentId) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person loggedUser = personService.findByPersonId(currentUser.getPersonId());

        String comment = commentService.deleteComment(commentId, loggedUser);

        if (comment == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(comment, HttpStatus.OK);
    }

}
