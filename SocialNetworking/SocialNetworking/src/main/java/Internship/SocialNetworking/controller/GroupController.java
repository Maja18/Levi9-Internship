package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.service.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/group", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GroupController {

    private final GroupServiceImpl groupService;

    @GetMapping("/getAllGroups")
    @RolesAllowed("ROLE_USER")
    ResponseEntity<List<GroupNW>> getAllGroups()
    {
        List<GroupNW> groups =groupService.getAllGroups();

        return groups == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(groups);
    }
}
