package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.PersonDTO;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public Person addFriend(Long personId, Long friendId) {
        Person person = personRepository.findByPersonId(personId);
        Person friend = personRepository.findByPersonId(friendId);


        if(person != null && friend != null){
            if(person.getPersonId() != friend.getPersonId()){
                List<Person> listFriends = person.getFriends();
                for (Person p: listFriends)
                {
                    if(p.getPersonId() == friendId){
                        return null;
                    }
                }
                listFriends.add(friend);
                person.setFriends(listFriends);
                return personRepository.save(person);
            }

            return null;
        }

        return null;
    }


       

    @Override
    public List<Person> getAllPersons() {
       return personRepository.findAll();

    }

}
