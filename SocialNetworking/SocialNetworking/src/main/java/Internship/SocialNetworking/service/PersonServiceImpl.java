package Internship.SocialNetworking.service;
import Internship.SocialNetworking.dto.FriendRequestDTO;
import Internship.SocialNetworking.models.*;
import Internship.SocialNetworking.dto.PersonDTO;
import Internship.SocialNetworking.repository.FriendRequestRepository;
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
    private final FriendRequestRepository friendRequestRepository;

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
            List<FriendRequest> friendRequestList = person.getFriendRequest();

            if(friendRequestList.stream().anyMatch(f -> f.getFriendId().equals(friendId))){
                    return null;
            }

            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setFriendId(friendId);
            friendRequest.setStatus(FriendRequestStatus.PENDING);
            friendRequestList.add(friendRequest);
            person.setFriendRequest(friendRequestList);

            return personRepository.save(person);
        }

        return null;
    }

    public Person approveFriendRequest(FriendRequestDTO friendRequestDTO, Long personId) {
        Person person = personRepository.findByPersonId(personId);
        FriendRequest friendRequest = friendRequestRepository.findByFriendRequestId(friendRequestDTO.getFriendRequestId());

        if(person != null && friendRequest != null && !Objects.equals(person.getPersonId(), friendRequest.getFriendId())){
            Person friend = personRepository.findByPersonId(friendRequest.getFriendId());
            List<Person> listFriends = person.getFriends();
            List<Person> listFriends1 = friend.getFriends();

            if(person.getFriendRequest().stream().anyMatch(fr-> fr.getFriendId().equals(friend.getPersonId()))){
                if(listFriends.stream().anyMatch(f -> f.getPersonId().equals(friend.getPersonId()))
                        && listFriends1.stream().anyMatch(f -> f.getPersonId().equals(personId))){
                    return null;
                }

                friendRequest.setStatus(friendRequestDTO.getStatus());

                if(friendRequestDTO.getStatus() == FriendRequestStatus.DECLINE
                        || friendRequestDTO.getStatus() == FriendRequestStatus.PENDING){
                    friendRequestRepository.save(friendRequest);
                    return friend;
                }

                listFriends.add(friend);
                listFriends1.add(person);
                person.setFriends(listFriends);
                friend.setFriends(listFriends1);
                friendRequestRepository.save(friendRequest);
                personRepository.save(person);
                return personRepository.save(friend);
            }
        }

        return null;
    }

    public Person removeFriend(Long personId, Long friendId) {
        Person person = personRepository.findByPersonId(personId);
        Person friend = personRepository.findByPersonId(friendId);

        if(person!= null && friend != null){
            List<Person> friendList = person.getFriends();
            List<Person> friendList1 = friend.getFriends();

            if(friendList.stream().anyMatch(f -> f.getPersonId().equals(friendId))
            && friendList1.stream().anyMatch(f -> f.getPersonId().equals(personId))){

                friendList.remove(friend);
                friendList1.remove(person);
                personRepository.save(friend);
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
            if(group.isPublic()) {
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
