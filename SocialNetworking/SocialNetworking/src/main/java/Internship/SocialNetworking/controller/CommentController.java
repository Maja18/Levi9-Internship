package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.CommentDTO;
import Internship.SocialNetworking.models.dto.FriendsDTO;
import Internship.SocialNetworking.service.CommentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController {
    private CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService){
        this.commentService = commentService;
    }

    @RequestMapping(value = "/add-comment")
    public ResponseEntity<Comment> addFriend(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.addComment(commentDTO);
        if (comment == null) {
            return new ResponseEntity<Comment>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

}
