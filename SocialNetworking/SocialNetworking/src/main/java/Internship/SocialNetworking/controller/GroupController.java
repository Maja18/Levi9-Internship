package Internship.SocialNetworking.controller;
import Internship.SocialNetworking.dto.GroupDTO;
import Internship.SocialNetworking.service.GroupServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/group", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GroupController {

    private final GroupServiceImpl groupService;



    @PostMapping(value = "/new")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> addNewGroup(@Valid @RequestBody GroupDTO groupDTO){
        return new ResponseEntity<String>(groupService.createGroup(groupDTO), HttpStatus.OK);
    }


}
