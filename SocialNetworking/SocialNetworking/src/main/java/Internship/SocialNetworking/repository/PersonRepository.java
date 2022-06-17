package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmailEquals(String email);
    Person findByPersonId(Long id);
}
