package Internship.SocialNetworking.controller;


import Internship.SocialNetworking.dto.NotificationDTO;
import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.NotificationRepository;
import Internship.SocialNetworking.service.NotificationServiceImpl;
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
@RequestMapping(value = "/api/notification")
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class NotificationController {

private final NotificationServiceImpl notificationService;
private final NotificationRepository notificationRepository;
private final PersonServiceImpl personService;

    @GetMapping("{groupId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<Notification>> getReceiverNotifications(@PathVariable Long groupId) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Long loggedPersonUserName=userWithId.getPersonId();
        var notificationList = notificationService.getAllNotifications(loggedPersonUserName,groupId);


        if (notificationList == null || notificationList.size() == 0) {
            return new ResponseEntity<List<Notification>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Notification>>(notificationList, HttpStatus.OK);

    }
    @PutMapping("{groupId}/{muteStatus}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> muteNotification(@PathVariable Long groupId,@PathVariable Long muteStatus)
    {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Long loggedPersonId=userWithId.getPersonId();
        String mutePerson=notificationService.muteNotification(groupId,loggedPersonId,muteStatus);
        if(mutePerson == null) {
            return new ResponseEntity<String>("There is no such group",HttpStatus.BAD_REQUEST);
        }
        if(mutePerson.equals("You are not member of that group")) {
            return new ResponseEntity<String>("You are not a member of that group",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Successfully turned notification off permanently!",HttpStatus.OK);
    }
}
