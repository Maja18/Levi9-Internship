package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonServiceImpl personService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Person person = personService.findByEmailEquals(email);
        if(person == null){
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        }
        else return person;

    }
}
