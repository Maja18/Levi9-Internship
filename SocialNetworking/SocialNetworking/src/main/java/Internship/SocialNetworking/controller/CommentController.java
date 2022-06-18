package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Comment;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.CommentDTO;
import Internship.SocialNetworking.models.dto.FriendsDTO;
import Internship.SocialNetworking.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;

    @RequestMapping(value = "/add-comment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.addComment(commentDTO);
        if (comment == null) {
            return new ResponseEntity<Comment>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

}
