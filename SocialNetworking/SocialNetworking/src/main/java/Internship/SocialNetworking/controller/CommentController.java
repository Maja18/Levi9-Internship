package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.CommentDTO;
import Internship.SocialNetworking.service.CommentServiceImpl;
import Internship.SocialNetworking.service.PersonServiceImpl;
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
public class CommentController {
    private final CommentServiceImpl commentService;
    private final PersonServiceImpl personService;

    @RequestMapping(value = "/add")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());

        Comment comment = commentService.addComment(commentDTO, userWithId.getPersonId());

        if (comment == null) {
            return new ResponseEntity<Comment>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    @GetMapping(value = "/{postId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<Comment>> showComment(@PathVariable(name = "postId") Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);

        if (comments == null) {
            return new ResponseEntity<List<Comment>>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

}
