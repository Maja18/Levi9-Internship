package Internship.SocialNetworking.controller;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.PostDTO;
import Internship.SocialNetworking.service.PersonServiceImpl;
import Internship.SocialNetworking.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;
    private final PersonServiceImpl personService;

    @PostMapping("/addNew")
    //@PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Post> addNewPost(@RequestBody PostDTO postDTO) {
        Post response = postService.addNewPost(postDTO);
        return (ResponseEntity<Post>) (response == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(response));

    }


}
