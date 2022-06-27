package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.GroupRequest;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.dto.GroupRequestDTO;
import Internship.SocialNetworking.service.GroupRequestServiceImpl;
import Internship.SocialNetworking.service.PersonServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/group-request")
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class GroupRequestController {

    private final GroupRequestServiceImpl groupRequestService;
    private final PersonServiceImpl personService;


    @GetMapping("")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<GroupRequest>> listGroupRequests() {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Long loggedPersonId=userWithId.getPersonId();
        var listOfRequests=groupRequestService.listAllRequests(loggedPersonId);


        if(listOfRequests.size() == 0 || listOfRequests == null) {
            return new ResponseEntity<List<GroupRequest>>(listOfRequests,HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<GroupRequest>>(listOfRequests,HttpStatus.OK);
    }


    @PutMapping("{requestId}/{approvalStatus}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> acceptOrRejectRequest(Long requestId,Long approvalStatus) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Long loggedPersonId=userWithId.getPersonId();
        String groupRequest=groupRequestService.acceptOrRejectRequest(requestId,loggedPersonId,approvalStatus);
        if(groupRequest == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        if(groupRequest.equals("Rejected")) {
            return new ResponseEntity<String>("User request rejected",HttpStatus.NOT_ACCEPTABLE);
        }
        if(groupRequest.equals("No request")) {
            return new ResponseEntity<String>("There is no such request",HttpStatus.NOT_FOUND);
        }
        if(groupRequest.equals("No permission")) {
            return new ResponseEntity<String>("You are not administrator of that group",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<String>("User request accepted and added to group",HttpStatus.ACCEPTED);
    }

}
