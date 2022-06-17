package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Authority;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.security.auth.JwtAuthenticationRequest;

public interface AuthorityService {
    public Authority findById(Long id);
    public Authority findByName(String name);
    Person getPerson(JwtAuthenticationRequest authenticationRequest);
}
