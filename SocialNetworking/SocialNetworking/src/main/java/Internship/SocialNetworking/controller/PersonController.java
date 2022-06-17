package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.PersonServiceImpl;
import Internship.SocialNetworking.service.iService.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/personController")
public class PersonController {

    private final PersonServiceImpl personService;

   public PersonController(PersonServiceImpl personService){
        this.personService = personService;
    }


@GetMapping("")
    public ResponseEntity<List<Person>> getAllPersons() {
        var listPersons=personService.getAllPersons();
       if(listPersons == null || listPersons.size() == 0) {
            return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Person>>(listPersons,HttpStatus.OK);

    }
    //if user is invalid then exception handler is called
    @PostMapping("")
    public ResponseEntity<Person> addPersons(@Valid @RequestBody Person person) {
        var per=personService.addPerson(person);

        return new ResponseEntity<Person>(person,HttpStatus.OK);
    }

    //this function prints validation errors
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
