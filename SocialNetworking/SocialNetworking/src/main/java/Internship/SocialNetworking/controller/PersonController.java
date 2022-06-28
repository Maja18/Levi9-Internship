package Internship.SocialNetworking.controller;


import Internship.SocialNetworking.dto.FriendRequestDTO;
import Internship.SocialNetworking.service.PersonServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import Internship.SocialNetworking.models.Person;

import Internship.SocialNetworking.dto.PersonDTO;

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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(add, HttpStatus.OK);
    }

    @PostMapping(value = "/approve-friend-request")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Person> approveFriendRequest(@RequestBody FriendRequestDTO friendRequestDTO) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());

        Person approve = personService.approveFriendRequest(friendRequestDTO, userWithId.getPersonId());

        if (approve == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(approve, HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove-friend/{friendId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Person> removeFriend(@PathVariable(name = "friendId") Long friendId) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());

        Person remove = personService.removeFriend(userWithId.getPersonId(), friendId);

        if (remove == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(remove, HttpStatus.OK);
    }

    @GetMapping("")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<Person>> getAllPersons() {
        var listPersons = personService.getAllPersons();
        if (listPersons == null || listPersons.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listPersons, HttpStatus.OK);

    }

    //if user is invalid then exception handler is called
    @PostMapping("registration")
    public ResponseEntity<Person> registerPersons(@RequestBody PersonDTO person) {
        var per=personService.registerPerson(person);
        if(per == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(per,HttpStatus.OK);

    }
    @PutMapping("")
   @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> alterPersons(@RequestBody PersonDTO person) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Long loggedPersonId=userWithId.getPersonId();
        //we will loop through list of all persons to check is there a person with such id
        var personToAlter=personService.alterPersonInformation(person,loggedPersonId);
        if(personToAlter == null) {
            return new ResponseEntity<>("Person with such id " +
                    "does not exist",HttpStatus.NOT_FOUND);
        }

        if(personToAlter.equals("No permission")) {
            return new ResponseEntity<>("You cannot change " +
                    "other user's information",HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("Successfully altered person",HttpStatus.OK);

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
            return new ResponseEntity<>("There is no such group",HttpStatus.BAD_REQUEST);
        }
        if(personToAdd.equals("Successfully added user to a group")) {
            return new ResponseEntity<>("You have successfully joined a group",HttpStatus.OK);
        }
        if(personToAdd.equals("Already a member")) {
            return new ResponseEntity<>("You are already a member",HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>("User added on pending and needs approval",HttpStatus.OK);
    }

    @DeleteMapping("{groupId}/{personId}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> deleteMembersOfGroup(@PathVariable Long groupId,@PathVariable Long personId) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Long loggedPersonId=userWithId.getPersonId();
        String deletedUser=personService.deletePersonFromGroup(groupId,personId,loggedPersonId);
        if(deletedUser == null) {
            return new ResponseEntity<>("Either group does not exist or user is not" +
                    "a member of a group",
                    HttpStatus.BAD_REQUEST);
        }
        if(deletedUser.equals("No permission")) {
            return new ResponseEntity<>("You are not an administrator" +
                    " of a group with specified id" , HttpStatus.FORBIDDEN);

        }
        return new ResponseEntity<>("Successfully deleted member of group",HttpStatus.OK);
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
