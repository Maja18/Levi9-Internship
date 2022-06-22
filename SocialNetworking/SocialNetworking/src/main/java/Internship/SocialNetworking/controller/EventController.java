package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.service.EventServiceImpl;
import Internship.SocialNetworking.service.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventService;

    private final GroupServiceImpl groupService;

    @PostMapping("/event")
    public ResponseEntity<String> newEvent(@Valid @RequestBody EventDTO eventDTO){
        return new ResponseEntity<String>(eventService.createEvent(eventDTO), HttpStatus.OK);
    }

    @GetMapping(value = "events/{groupId}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<Event>> eventsGroup(@PathVariable("groupId") Long groupId){
        return new ResponseEntity<List<Event>>(groupService.groupEvents(groupId), HttpStatus.OK);
    }
}
