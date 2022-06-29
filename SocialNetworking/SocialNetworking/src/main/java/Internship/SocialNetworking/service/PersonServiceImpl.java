package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.FriendInfoDTO;
import Internship.SocialNetworking.mappers.PersonMapper;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.GroupRequest;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.RequestStatus;

import Internship.SocialNetworking.dto.FriendRequestDTO;
import Internship.SocialNetworking.models.*;

import Internship.SocialNetworking.dto.PersonDTO;
import Internship.SocialNetworking.repository.FriendRequestRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.GroupRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final  PersonRepository personRepository;

    private  final GroupRequestRepository groupRequestRepository;
    private final GroupRepository groupRepository;

    private final PersonMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final FriendRequestRepository friendRequestRepository;
    private final PersonMapper personMapper;


    @Override
    public Person findByEmailEquals(String email) {
        return personRepository.findByEmailEquals(email);
    }

    @Override
    public Person findByPersonId(Long id) {
        return personRepository.findByPersonId(id);
    }

    @Override
    public PersonDTO registerPerson(PersonDTO person) {

             Person mappedPerson=mapper.personDTOtoPerson(person);
             if(personRepository.findByEmailEquals(mappedPerson.getEmail()) == null) {
                 //we immediately hash a password
                 mappedPerson.setPassword(passwordEncoder.encode(person.getPassword()));
                 return mapper.personToPersonDTO(personRepository.save(mappedPerson));
             }
            return null;

    }

    public FriendInfoDTO sendFriendRequest(Long personId, Long friendId) {
        Person person = personRepository.findByPersonId(personId);
        Person friend = personRepository.findByPersonId(friendId);

        if(person != null && friend != null && !Objects.equals(person.getPersonId(), friend.getPersonId())){
            List<FriendRequest> friendRequestList = friend.getFriendRequest();

            if(friendRequestList.stream().anyMatch(f -> f.getFriendId().equals(personId) && !f.getDeleted())
                || IsFriendshipExist(person, friend)){

                    log.info("The friend request already exist or friendship already exist!");
                    return null;
            }

            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setFriendId(personId);
            friendRequest.setStatus(FriendRequestStatus.PENDING);
            friendRequest.setDeleted(false);
            friendRequestList.add(friendRequest);
            friend.setFriendRequest(friendRequestList);
            personRepository.save(friend);

            return personMapper.personToFriendInfoDTO(friend);
        }

        log.info("IDs don't exist or IDs are same!");
        return null;
    }

    public FriendInfoDTO approveFriendRequest(FriendRequestDTO friendRequestDTO, Long personId) {
        Person person = personRepository.findByPersonId(personId);
        FriendRequest friendRequest = friendRequestRepository.findByFriendRequestId(friendRequestDTO.getFriendRequestId());

        if(validation(person, friendRequest)){
            Person friend = personRepository.findByPersonId(friendRequest.getFriendId());
            List<Person> personListFriends = person.getFriends();
            List<Person> friendListFriends = friend.getFriends();

            if(isPersonInFriendRequest(person, friend) && !IsFriendshipExist(person, friend)){

                friendRequest.setStatus(friendRequestDTO.getStatus());

                if(friendRequestDTO.getStatus() == FriendRequestStatus.DECLINE){
                    friendRequest.setDeleted(true);
                    friendRequestRepository.save(friendRequest);

                    return personMapper.personToFriendInfoDTO(friend);
                }else if(friendRequestDTO.getStatus() == FriendRequestStatus.PENDING) {
                    log.info("Friend request is already in pending!");
                    return null;
                }

                personListFriends.add(friend);
                friendListFriends.add(person);
                person.setFriends(personListFriends);
                friend.setFriends(friendListFriends);
                friendRequest.setDeleted(true);
                friendRequestRepository.save(friendRequest);
                personRepository.save(person);
                personRepository.save(friend);

                return personMapper.personToFriendInfoDTO(friend);
            }
        }

        log.info("Logged person or friend request doesn't exist!");
        return null;
    }

    private boolean validation(Person person, FriendRequest friendRequest){
        return person != null && friendRequest != null
                && !friendRequest.getDeleted()
                && !Objects.equals(person.getPersonId(), friendRequest.getFriendId());
    }

    private boolean isPersonInFriendRequest(Person person, Person friend){
        return person.getFriendRequest().stream().anyMatch(fr-> fr.getFriendId().equals(friend.getPersonId()));
    }

    private boolean IsFriendshipExist(Person person, Person friend){
        return person.getFriends().stream().anyMatch(f -> f.getPersonId().equals(friend.getPersonId()))
                && friend.getFriends().stream().anyMatch(f -> f.getPersonId().equals(person.getPersonId()));
    }

    public FriendInfoDTO removeFriend(Long personId, Long friendId) {
        Person person = personRepository.findByPersonId(personId);
        Person friend = personRepository.findByPersonId(friendId);

        if(person != null && friend != null){
            List<Person> friendList = person.getFriends();
            List<Person> friendList1 = friend.getFriends();

            if(IsFriendshipExist(person, friend)){

                friendList.remove(friend);
                friendList1.remove(person);
                personRepository.save(friend);
                personRepository.save(person);

                return personMapper.personToFriendInfoDTO(friend);
            }

            log.info("Friendship doesn't exist!");
            return null;
        }

        log.info("One of ids doesn't exist!");
        return null;
    }

    @Override
    public String addPersonToGroup(Long groupId,Long personId) {
        GroupNW group=groupRepository.findByGroupId(groupId);
        if(group!=null) {
            Person addingPerson=personRepository.findByPersonId(personId);

            if(group.getIsPublic()) {
                for (Person member : group.getMembers()) {
                    if (Objects.equals(personId, member.getPersonId())) {
                        return "Already a member";
                    }
                }
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
         if(Objects.equals(administratorId, group.getCreatorId())) {
             for (Person member : group.getMembers()) {
                 Long personMemberId = member.getPersonId();
                 if (Objects.equals(personId, personMemberId)) {
                     group.getMembers().remove(member);
                     personRepository.save(member);
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
        Person alteringPerson=personRepository.findByPersonId(userId);
        //checking whether user with specified id exists at all
         alteringPerson.setPersonId(userId);
         alteringPerson.setName(person.getName());
         alteringPerson.setSurname(person.getSurname());
         alteringPerson.setEmail(person.getEmail());
         alteringPerson.setUsername(person.getUsername());
         alteringPerson.setPassword(passwordEncoder.encode(person.getPassword()));
                //we save changes to the database
                personRepository.save(alteringPerson);
                return "Successfully updated user";


    }

    @Override
    public List<Person> getAllPersons() {
       return personRepository.findAll();
    }

}
