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

@RestController
@RequestMapping(value = "api/group", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController {

    private GroupServiceImpl groupService;

    public GroupController(GroupServiceImpl groupService){
        this.groupService = groupService;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<String> addNewGroup(@RequestBody GroupDTO groupDTO){
        return new ResponseEntity<String>(groupService.createGroup(groupDTO), HttpStatus.OK);
    }

}
