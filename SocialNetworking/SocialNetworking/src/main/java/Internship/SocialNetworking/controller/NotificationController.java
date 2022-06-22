package Internship.SocialNetworking.controller;


import Internship.SocialNetworking.dto.NotificationDTO;
import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;
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
private final PersonServiceImpl personService;

    @GetMapping("")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<Notification>> getReceiverNotifications() {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        String loggedPersonUserName=userWithId.getUsername();
        var notificationList = notificationService.getAllNotifications(loggedPersonUserName);

        if (notificationList == null || notificationList.size() == 0) {
            return new ResponseEntity<List<Notification>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Notification>>(notificationList, HttpStatus.OK);

    }
    @PutMapping("")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> muteNotification(@RequestBody NotificationDTO notificationModel)
    {
        String mutePerson=notificationService.muteNotification(notificationModel);
        if(mutePerson == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Successfully turned notification off permanently!",HttpStatus.OK);
    }
}
