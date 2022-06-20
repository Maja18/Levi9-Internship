package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Person;

import Internship.SocialNetworking.models.dto.PersonDTO;

import org.springframework.http.ResponseEntity;


import java.util.List;

public interface PersonService {
    Person findByEmailEquals(String email);
    Person findByPersonId(Long id);
    Person addPerson(PersonDTO person);
    Person addFriend(Long personId, Long friendId);

    String addPersonToGroup(Long groupId,Long personId);

    String deletePerson(Long groupId,Long personId);
    List<Person> getAllPersons();
}
