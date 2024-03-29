package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.dto.UserTokenStateDTO;
import Internship.SocialNetworking.security.TokenUtils;
import Internship.SocialNetworking.security.auth.JwtAuthenticationRequest;
import Internship.SocialNetworking.service.AuthorityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final TokenUtils tokenUtils;
    private final AuthorityServiceImpl authorityService;

    @RolesAllowed("ROLE_USER")
    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                       HttpServletResponse response) {
        Person person = authorityService.getPerson(authenticationRequest);
        String jwt = tokenUtils.generateToken(person.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
    }


}
