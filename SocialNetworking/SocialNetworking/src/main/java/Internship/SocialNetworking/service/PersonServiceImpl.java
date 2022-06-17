package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.PersonDTO;
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


   private PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }


    @Override
    public Person findByEmailEquals(String email) {
        return personRepository.findByEmailEquals(email);
    }

    @Override
    public Person addPerson(PersonDTO person) {
        Person pers = personRepository.findByPersonId(person.getPersonId());

          if (pers == null) {
               Person mappedPerson=new Person();
              mappedPerson.setPersonId(person.getPersonId());
              mappedPerson.setName(person.getName());
              mappedPerson.setSurname(person.getSurname());
              mappedPerson.setEmail(person.getEmail());
              mappedPerson.setUsername(person.getUsername());
              mappedPerson.setPassword(person.getPassword());
              mappedPerson.setRole(person.getRole());

              return personRepository.save(mappedPerson);
            }
            return null;

    }

    @Override
    public List<Person> getAllPersons() {
       return personRepository.findAll();

    }

}
