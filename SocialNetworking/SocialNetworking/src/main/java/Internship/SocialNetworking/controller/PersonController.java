package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.FriendsDTO;
import Internship.SocialNetworking.service.PersonServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/person")
public class PersonController {

    private PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService){
        this.personService = personService;
    }

    @PostMapping(value = "/add-friend")
    public ResponseEntity<Person> addFriend(@RequestBody FriendsDTO friendsDTO){
        Person add = personService.addFriend(friendsDTO.getPersonId(), friendsDTO.getFriendId());

        if(add == null){
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Person>(add, HttpStatus.OK);
    }

}
