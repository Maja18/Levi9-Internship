package Internship.SocialNetworking.controller;
import Internship.SocialNetworking.dto.GroupDTO;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.service.GroupServiceImpl;
import Internship.SocialNetworking.service.PersonServiceImpl;
import Internship.SocialNetworking.service.iService.EventService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/group", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class GroupController {

    private final GroupServiceImpl groupService;

    private final PersonServiceImpl personService;

    @PostMapping
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<GroupDTO> addNewGroup(@Valid @RequestBody GroupDTO groupDTO){
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        return new ResponseEntity<>(groupService.createGroup(groupDTO, userWithId.getPersonId()), HttpStatus.OK);
    }

    
}
