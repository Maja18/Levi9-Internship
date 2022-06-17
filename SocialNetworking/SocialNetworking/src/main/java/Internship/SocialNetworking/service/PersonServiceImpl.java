package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonServiceImpl implements PersonService {


   private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }


    @Override
    public Person findByEmailEquals(String email) {
        return personRepository.findByEmailEquals(email);
    }

    @Override
    public Person addPerson(Person person) {

          if (personRepository.existsById(person.getPersonId())) {
                return null;
            }
            var pers= personRepository.save(person);
            return pers;
    }

    @Override
    public List<Person> getAllPersons() {
       return personRepository.findAll();

    }

}
