package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Person;


import Internship.SocialNetworking.models.dto.GroupRequestDTO;
import Internship.SocialNetworking.models.dto.PersonDTO;

import org.springframework.http.ResponseEntity;

import Internship.SocialNetworking.dto.PersonDTO;



import java.util.List;

public interface PersonService {
    Person findByEmailEquals(String email);
    Person findByPersonId(Long id);
    Person addPerson(PersonDTO person);
    Person addFriend(Long personId, Long friendId);

    String addPersonToGroup(Long groupId,Long personId);


    String alterPersonInformation(PersonDTO person, Long userId,List<Person> listPersons);

    String deletePersonFromGroup(Long groupId,Long personId,Long administratorId);

    List<Person> getAllPersons();
    Person removeFriend(Long personId, Long friendId);
}
