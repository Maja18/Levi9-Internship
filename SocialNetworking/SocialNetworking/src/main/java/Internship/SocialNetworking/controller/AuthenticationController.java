package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.UserTokenStateDTO;
import Internship.SocialNetworking.security.TokenUtils;
import Internship.SocialNetworking.security.auth.JwtAuthenticationRequest;
import Internship.SocialNetworking.service.AuthorityServiceImpl;
import Internship.SocialNetworking.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private  TokenUtils tokenUtils;

    private  PersonServiceImpl personService;

    private  PasswordEncoder passwordEncoder;

    private AuthorityServiceImpl authorityService;

    @Autowired
    public AuthenticationController(TokenUtils tokenUtils , PersonServiceImpl personService, PasswordEncoder passwordEncoder, AuthorityServiceImpl authorityService) {
        this.tokenUtils = tokenUtils;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                                       HttpServletResponse response) {
        Person person = authorityService.getPerson(authenticationRequest);
        String jwt = tokenUtils.generateToken(person.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
    }

    @GetMapping("/authority")
    //@PreAuthorize("hasRole('MEMBER')")
    ResponseEntity<Person> getMyAccount()
    {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());

        return (ResponseEntity<Person>) (userWithId == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(userWithId));
    }
}
