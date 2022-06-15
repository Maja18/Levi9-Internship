package Internship.SocialNetworking.Repository;

import Internship.SocialNetworking.Models.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {
 //Person findByIdEquals(Long PersonId);
}
