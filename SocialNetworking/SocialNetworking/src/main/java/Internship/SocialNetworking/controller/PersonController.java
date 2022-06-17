package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Person;

import Internship.SocialNetworking.models.dto.PersonDTO;
import Internship.SocialNetworking.repository.PersonRepository;

import Internship.SocialNetworking.models.dto.FriendsDTO;

import Internship.SocialNetworking.service.PersonServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping(value = "/api/person")

public class PersonController {

    private PersonServiceImpl personService;

    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/add-friend")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Person> addFriend(@RequestBody FriendsDTO friendsDTO) {
        Person add = personService.addFriend(friendsDTO.getPersonId(), friendsDTO.getFriendId());

        if (add == null) {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Person>(add, HttpStatus.OK);
    }

    @GetMapping("")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<Person>> getAllPersons() {
        var listPersons = personService.getAllPersons();
        if (listPersons == null || listPersons.size() == 0) {
            return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Person>>(listPersons, HttpStatus.OK);

    }

    //if user is invalid then exception handler is called
    @PostMapping("")

    public ResponseEntity<Person> addPersons(@Valid @RequestBody PersonDTO person) {
        var per=personService.addPerson(person);
        if(per == null) {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Person>(per,HttpStatus.OK);

    }

    //this function prints validation errors
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
