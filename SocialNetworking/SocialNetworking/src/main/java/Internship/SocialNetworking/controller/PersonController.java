package Internship.SocialNetworking.controller;

import Internship.SocialNetworking.service.PersonServiceImpl;
import Internship.SocialNetworking.service.iService.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

   private PersonServiceImpl personService;


    public PersonController(PersonServiceImpl personService ){
        this.personService = personService ;

    }




}
