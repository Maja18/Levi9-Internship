package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.dto.UserTokenStateDTO;
import Internship.SocialNetworking.security.TokenUtils;
import Internship.SocialNetworking.security.auth.JwtAuthenticationRequest;
import Internship.SocialNetworking.service.AuthorityServiceImpl;
import Internship.SocialNetworking.service.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final TokenUtils tokenUtils;

    private final PersonServiceImpl personService;

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

    @GetMapping("/authority")
    @RolesAllowed("ROLE_USER")
    ResponseEntity<Person> getMyAccount()
    {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());

        return (userWithId == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(userWithId));
    }
}
