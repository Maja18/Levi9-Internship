package Internship.SocialNetworking.service;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.GroupRequest;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.RequestStatus;
import Internship.SocialNetworking.dto.PersonDTO;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.GroupRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final  PersonRepository personRepository;

    private  final GroupRequestRepository groupRequestRepository;
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
        Person findPerson = personRepository.findByPersonId(person.getPersonId());

          if (findPerson == null) {
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


        if(person != null && friend != null && !Objects.equals(person.getPersonId(), friend.getPersonId())){
            List<Person> listFriends = person.getFriends();

            if(listFriends.stream().anyMatch(f -> f.getPersonId().equals(friendId))){
                    return null;
            }

            listFriends.add(friend);
            person.setFriends(listFriends);
            return personRepository.save(person);
        }

        return null;
    }

    public Person removeFriend(Long personId, Long friendId) {
        Person person = personRepository.findByPersonId(personId);
        Person friend = personRepository.findByPersonId(friendId);

        if(person!= null && friend != null){
            List<Person> friendList = person.getFriends();
            if(friendList.stream().anyMatch(f -> f.getPersonId().equals(friendId))){
                friendList.remove(friend);
                return personRepository.save(person);
            }
        }
        return null;
    }

    @Override
    public String addPersonToGroup(Long groupId,Long personId) {
        GroupNW group=groupRepository.findByGroupId(groupId);
        if(group!=null) {
            Person addingPerson=personRepository.findByPersonId(personId);
            if(group.getIsPublic()) {
                group.getMembers().add(addingPerson);
                personRepository.save(addingPerson);
                return "Successfully added user to a group";
            }
            else {
                //creating a request to send
                GroupRequest accessRequest=new GroupRequest();
                accessRequest.setGroupId(groupId);
                accessRequest.setRequestStatus(RequestStatus.PENDING);
                accessRequest.setCreatorId(addingPerson.getPersonId());
                groupRequestRepository.save(accessRequest);
                return "Added on pending";
            }
        }
        return null;
    }

    @Override
    public String deletePersonFromGroup(Long groupId,Long personId,Long administratorId) {

        GroupNW group = groupRepository.findByGroupId(groupId);

     if (group != null) {
         if(administratorId == group.getCreatorId()) {
             for (int i = 0; i < group.getMembers().size(); i++) {
                 Long personMemberId = group.getMembers().get(i).getPersonId();
                 if (personId == personMemberId) {
                     Person personToRemove = group.getMembers().get(i);
                     group.getMembers().remove(personToRemove);
                     personRepository.save(personToRemove);
                     return "Successfully deleted member of group";
                 }
             }
         }
         else return "No permission";
        }
        return null;
    }

    @Override
    public String alterPersonInformation(PersonDTO person, Long userId) {
        Person alteringPerson=personRepository.findByPersonId(person.getPersonId());
        //checking whether user with specified id exists at all
        if(alteringPerson != null) {
            //checking whether user is allowed to change information
            if (alteringPerson.getPersonId() == userId) {
                alteringPerson.setPersonId(person.getPersonId());
                alteringPerson.setName(person.getName());
                alteringPerson.setSurname(person.getSurname());
                alteringPerson.setEmail(person.getEmail());
                alteringPerson.setUsername(person.getUsername());
                alteringPerson.setPassword(person.getPassword());
                        //we save changes to the database
                personRepository.save(alteringPerson);
                return "Successfully updated user";

                }
            return "No permission";
        }
       return null;
    }

    @Override
    public List<Person> getAllPersons() {
       return personRepository.findAll();
    }

}
