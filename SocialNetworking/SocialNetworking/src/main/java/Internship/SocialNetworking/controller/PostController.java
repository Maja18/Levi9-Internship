package Internship.SocialNetworking.controller;
import Internship.SocialNetworking.dto.HidePostDTO;
import Internship.SocialNetworking.dto.PostDTO;
import Internship.SocialNetworking.dto.PostInfoDTO;
import Internship.SocialNetworking.exceptions.PersonException;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.service.PersonServiceImpl;
import Internship.SocialNetworking.service.PostServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/post", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class PostController {

    private final PostServiceImpl postService;
    private final PersonServiceImpl personService;

    @PostMapping
    @RolesAllowed({ "ROLE_USER", "ROLE_MEMBER" })
    public ResponseEntity<PostDTO> addNewPost(@RequestBody PostDTO postDTO) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        Person loggedPerson = (Person) currentUser.getPrincipal();
        PostDTO response = postService.addNewPost(postDTO, loggedPerson);

        return (ResponseEntity<PostDTO>) (response == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(response));

    }

    @GetMapping("/posts/{user-id}")
    @RolesAllowed({ "ROLE_USER", "ROLE_MEMBER" })
    ResponseEntity<List<PostDTO>> getAllUserPosts(@PathVariable(name="user-id") Long userId)
    {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        Person loggedPerson = (Person) currentUser.getPrincipal();
        Person person = personService.findByPersonId(userId);
        if (person == null){
            throw new PersonException(userId, "Person with given id doesn't exist");
        }
        List<PostDTO> posts =postService.getAllUserPosts(userId, loggedPerson);

        return posts == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(posts);
    }

    @PutMapping("/hide")
    @RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
    ResponseEntity<PostInfoDTO> hidePost(@RequestBody HidePostDTO hidePostDTO)
    {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        PostInfoDTO post = postService.hidePost(hidePostDTO, userWithId.getPersonId());

        if(post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/friends-posts")
    @RolesAllowed({ "ROLE_USER", "ROLE_MEMBER" })
    ResponseEntity<List<PostDTO>> getAllFriendPosts()
    {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person loggedPerson = personService.findByPersonId(currentUser.getPersonId());
        List<PostDTO> posts = postService.getAllFriendPosts(loggedPerson);

        return posts == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(posts);
    }

    @PostMapping(value = "/save-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({ "ROLE_USER", "ROLE_MEMBER" })
    public List<String> saveImage(@RequestParam("file") List<MultipartFile> multipartFiles ) throws IOException {

        return postService.getFileNames(multipartFiles);
    }
}
