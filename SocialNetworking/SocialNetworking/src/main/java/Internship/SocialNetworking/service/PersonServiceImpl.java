package Internship.SocialNetworking.service;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.dto.PersonDTO;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.repository.PostRepository;
import Internship.SocialNetworking.service.iService.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {







    private final  PersonRepository personRepository;
    private final GroupRepository groupRepository;




    @Override
    public Person findByEmailEquals(String email) {
        return personRepository.findByEmailEquals(email);
    }

    @Override
    public Person findByPersonId(Long id) {
        return personRepository.findByPersonId(id);
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
    public String deletePerson(Long groupId,Long personId) {

        GroupNW group = groupRepository.findByGroupId(groupId);


     if (group != null) {
         for (int i = 0; i < group.getMembers().size(); i++) {
             Long personMemberId = group.getMembers().get(i).getPersonId();
             if (personId == personMemberId) {
                 Person personToRemove=group.getMembers().get(i);
                 group.getMembers().remove(personToRemove);
                 personRepository.save(personToRemove);
                 return "Successfully deleted member of group";
             }
         }
        }
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
       return personRepository.findAll();
    }


}
