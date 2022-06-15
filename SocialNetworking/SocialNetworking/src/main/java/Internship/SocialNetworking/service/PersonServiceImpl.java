package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findByEmailEquals(String email) {
        return personRepository.findByEmailEquals(email);
    }
}
