package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.service.EventServiceImpl;
import Internship.SocialNetworking.service.GroupServiceImpl;
import Internship.SocialNetworking.service.PersonServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/event")
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class EventController {

    private final EventServiceImpl eventService;
    private final PersonServiceImpl personService;

    private final GroupServiceImpl groupService;

    @PostMapping
    public ResponseEntity<String> newEvent(@Valid @RequestBody EventDTO eventDTO){
        return new ResponseEntity<String>(eventService.createEvent(eventDTO), HttpStatus.OK);
    }

    @GetMapping(value = "events/{groupId}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<Event>> eventsGroup(@PathVariable("groupId") Long groupId){
        return new ResponseEntity<List<Event>>(groupService.groupEvents(groupId), HttpStatus.OK);
    }
    @PutMapping("{groupId}/{eventId}/{presenceStatus}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> goingToEventOrNot(@PathVariable Long groupId,@PathVariable Long eventId,@PathVariable Long presenceStatus) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Long loggedPersonId=userWithId.getPersonId();
        String presentUser=eventService.goToEventOrNot(groupId,loggedPersonId,eventId,presenceStatus);
        if(presentUser == null) {
            return new ResponseEntity<>("No such group",HttpStatus.NOT_FOUND);
        }
        if(presentUser.equals("Not a member")) {
            return new ResponseEntity<>("You are not a member of that group",HttpStatus.FORBIDDEN);
        }
        if(presentUser.equals("Not going")) {
            return new ResponseEntity<>("You have declared not to go to an event",HttpStatus.OK);
        }
        if(presentUser.equals("No such event")) {
            return new ResponseEntity<>("There is no such event" +
                    " in a group with specified id",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("You have successfully confirmed your presence",HttpStatus.ACCEPTED);
    }
}
