package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Person;

import java.util.List;

public interface PersonService {
    Person findByEmailEquals(String email);
    Person addPerson(Person person);
    List<Person> getAllPersons();
}
