package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Authority;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.AuthorityRepository;
import Internship.SocialNetworking.security.auth.JwtAuthenticationRequest;
import Internship.SocialNetworking.service.iService.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl  implements AuthorityService {

    private AuthenticationManager authenticationManager;

    private AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthenticationManager authenticationManager,AuthorityRepository authorityRepository ){
        this.authenticationManager = authenticationManager;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority findById(Long id) {
        return authorityRepository.findByAuthorityId(id);
    }

    @Override
    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }

    @Override
    public Person getPerson(JwtAuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Person person = (Person) authentication.getPrincipal();
        return person;
    }
}
