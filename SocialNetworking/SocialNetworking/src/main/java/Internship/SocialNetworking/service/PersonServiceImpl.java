package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
