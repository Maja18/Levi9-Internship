package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.service.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/event")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl eventService;

    @PostMapping("/new")
    public ResponseEntity<String> newEvent(@Valid @RequestBody EventDTO eventDTO){
        return new ResponseEntity<String>(eventService.createEvent(eventDTO), HttpStatus.OK);
    }
}
