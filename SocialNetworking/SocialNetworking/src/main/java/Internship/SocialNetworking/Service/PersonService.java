package Internship.SocialNetworking.Service;

import Internship.SocialNetworking.Models.Person;
import Internship.SocialNetworking.Repository.PersonRepository;
import Internship.SocialNetworking.Service.IService.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.Collection;
import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
private PersonRepository personRepository;
    @Override
    public Person AddPerson(Person person) {
        if(!personRepository.existsById(person.getPersonId())) {
            personRepository.save(person);
            return person;
        }
        return null;
    }

    @Override
    public List<Person> GetAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public Person GetPerson(Long PersonId) {
        return null;
    }

}
