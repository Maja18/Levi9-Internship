package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.PersonDTO;

import java.util.List;

public interface PersonService {
    Person findByEmailEquals(String email);
    Person addPerson(PersonDTO person);
    List<Person> getAllPersons();
}
