package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Post;
import Internship.SocialNetworking.models.dto.PostDTO;
import Internship.SocialNetworking.service.GroupServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/group", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController {

    private GroupServiceImpl groupService;

    public GroupController(GroupServiceImpl groupService){
        this.groupService = groupService;
    }

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
