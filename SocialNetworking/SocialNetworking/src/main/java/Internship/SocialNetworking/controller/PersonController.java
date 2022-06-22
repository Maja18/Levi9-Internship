package Internship.SocialNetworking.controller;


import Internship.SocialNetworking.service.PersonServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import Internship.SocialNetworking.models.Person;

import Internship.SocialNetworking.models.dto.PersonDTO;

import Internship.SocialNetworking.dto.FriendsDTO;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class PersonController {
    private final  PersonServiceImpl personService;

    @PostMapping(value = "/add-friend")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Person> addFriend(@RequestBody FriendsDTO friendsDTO) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());

        Person add = personService.addFriend(userWithId.getPersonId(), friendsDTO.getFriendId());

        if (add == null) {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Person>(add, HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove-friend/{friendId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Person> removeFriend(@PathVariable(name = "friendId") Long friendId) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());

        Person remove = personService.removeFriend(userWithId.getPersonId(), friendId);

        if (remove == null) {
            return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Person>(remove, HttpStatus.OK);
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
    @PostMapping("add-to-group/{groupId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> addPersonToGroup(@PathVariable Long groupId) {
        //getting data from logged user
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Long loggedPersonId=userWithId.getPersonId();
        String personToAdd=personService.addPersonToGroup(groupId,loggedPersonId);
        if(personToAdd== null) {
            return new ResponseEntity<String>("There is no such group",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("User added on pending and needs approval",HttpStatus.OK);
    }

    @DeleteMapping("{groupId}/{personId}")
    public ResponseEntity<String> deleteMembersOfGroup(@PathVariable Long groupId,@PathVariable Long personId) {

        String deletedUser=personService.deletePerson(groupId,personId);
        if(deletedUser == null) {
            return new ResponseEntity<String>("Either group does not exist or user is not" +
                    "a member of a group",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Successfully deleted member of group",HttpStatus.OK);
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
