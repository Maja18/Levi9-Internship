package Internship.SocialNetworking.controller;


import Internship.SocialNetworking.dto.MuteRequestDTO;
import Internship.SocialNetworking.models.MuteRequest;
import Internship.SocialNetworking.service.MuteRequestServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value = "/api/notification")
@RequiredArgsConstructor
@SecurityRequirement(name = "javainuseapi")
public class NotificationController {

    private final MuteRequestServiceImpl muteRequestService;


    @PostMapping
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<MuteRequestDTO> addNewNotification(@RequestBody MuteRequestDTO muteRequestDTO){

        return new ResponseEntity<>(muteRequestService.muteGroup(muteRequestDTO), HttpStatus.OK);

    }


}
