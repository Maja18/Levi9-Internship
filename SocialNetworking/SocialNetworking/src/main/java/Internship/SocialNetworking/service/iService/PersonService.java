package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Person;

public interface PersonService {
    Person findByEmailEquals(String email);
    Person findByPersonId(Long id);

}
