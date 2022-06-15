package Internship.SocialNetworking.Controller;

import Internship.SocialNetworking.Models.Person;
import Internship.SocialNetworking.Repository.PersonRepository;
import Internship.SocialNetworking.Service.IService.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PersonController {

    @Autowired
    private IPersonService personService;


    @GetMapping("persons")
    public ResponseEntity<Person> GetAllPersons() {
         personService.GetAllPersons();
         return new ResponseEntity<Person>(HttpStatus.ACCEPTED);
    }
    @PostMapping("person")
    public ResponseEntity<Person> InsertPerson(@RequestBody Person person) {
      var pers =  personService.AddPerson(person);
      if(pers == null)
          return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);

      return new ResponseEntity<Person>(HttpStatus.CREATED);
    }
    @GetMapping("person/{id}")
    public ResponseEntity<Person> GetPerson(Long PersonId) {
       Person per=personService.GetPerson(PersonId);
       if(per == null)
           return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);

       return new ResponseEntity<Person>(HttpStatus.FOUND);
    }
}
