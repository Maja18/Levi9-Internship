package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository {
    Person findByEmailEquals(String email);
}
