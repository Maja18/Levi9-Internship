package Internship.SocialNetworking.controller;
import Internship.SocialNetworking.dto.GroupDTO;
import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.service.GroupServiceImpl;
import Internship.SocialNetworking.service.iService.EventService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/group", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class GroupController {

    private final GroupServiceImpl groupService;

    private final EventService eventService;



    @PostMapping(value = "/new")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> addNewGroup(@Valid @RequestBody GroupDTO groupDTO){
        return new ResponseEntity<String>(groupService.createGroup(groupDTO), HttpStatus.OK);
    }

    
}
