package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Person;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {
    Person findByEmailEquals(String email);

    Person addFriend(Long personId, Long friendId);
    Person addPerson(Person person);
    List<Person> getAllPersons();
}
