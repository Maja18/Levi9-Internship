package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Person;
import org.springframework.http.ResponseEntity;

public interface PersonService {
    Person findByEmailEquals(String email);

    Person addFriend(Long personId, Long friendId);
}
