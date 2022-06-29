package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.FriendRequestDTO;
import Internship.SocialNetworking.mappers.PersonMapper;
import Internship.SocialNetworking.models.FriendRequest;
import Internship.SocialNetworking.models.FriendRequestStatus;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.FriendRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class PersonServiceTests {

    @InjectMocks
    private PersonServiceImpl personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Spy
    PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    @Before
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendFriendRequest(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friend.setFriendRequest(friendRequestList);
        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        Assertions.assertNotNull(personService.sendFriendRequest(loggedPerson.getPersonId(), friend.getPersonId()));
    }

    @Test
    void sendFriendRequestWhereIDsAreSame(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(1L);
        friend.setName("Ivan");

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friend.setFriendRequest(friendRequestList);
        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        Assertions.assertNull(personService.sendFriendRequest(loggedPerson.getPersonId(), friend.getPersonId()));
    }

    @Test
    void sendFriendRequestWhereFriendshipExist(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friend.setFriendRequest(friendRequestList);
        List<Person> friendList = new ArrayList<>();
        List<Person> friendList1 = new ArrayList<>();
        friendList.add(friend);
        friendList1.add(loggedPerson);
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList1);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        Assertions.assertNull(personService.sendFriendRequest(loggedPerson.getPersonId(), friend.getPersonId()));
    }

    @Test
    void acceptedFriendRequest(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendId(loggedPerson.getPersonId());
        friendRequest.setFriendRequestId(1L);
        friendRequest.setDeleted(false);
        friendRequest.setStatus(FriendRequestStatus.PENDING);

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(friendRequest);
        friend.setFriendRequest(friendRequestList);

        FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
        friendRequestDTO.setFriendRequestId(friendRequest.getFriendRequestId());
        friendRequestDTO.setStatus(FriendRequestStatus.ACCEPTED);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        when(friendRequestRepository.findByFriendRequestId(friendRequest.getFriendRequestId())).thenReturn(friendRequest);
        Assertions.assertNotNull(personService.approveFriendRequest(friendRequestDTO, friend.getPersonId()));
    }

    @Test
    void declineFriendRequest(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendId(loggedPerson.getPersonId());
        friendRequest.setFriendRequestId(1L);
        friendRequest.setDeleted(false);
        friendRequest.setStatus(FriendRequestStatus.PENDING);

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(friendRequest);
        friend.setFriendRequest(friendRequestList);

        FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
        friendRequestDTO.setFriendRequestId(friendRequest.getFriendRequestId());
        friendRequestDTO.setStatus(FriendRequestStatus.DECLINE);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        when(friendRequestRepository.findByFriendRequestId(friendRequest.getFriendRequestId())).thenReturn(friendRequest);
        Assertions.assertNotNull(personService.approveFriendRequest(friendRequestDTO, friend.getPersonId()));
    }

    @Test
    void pendingFriendRequest(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendId(loggedPerson.getPersonId());
        friendRequest.setFriendRequestId(1L);
        friendRequest.setDeleted(false);
        friendRequest.setStatus(FriendRequestStatus.PENDING);

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friendRequestList.add(friendRequest);
        friend.setFriendRequest(friendRequestList);

        FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
        friendRequestDTO.setFriendRequestId(friendRequest.getFriendRequestId());
        friendRequestDTO.setStatus(FriendRequestStatus.PENDING);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        when(friendRequestRepository.findByFriendRequestId(friendRequest.getFriendRequestId())).thenReturn(friendRequest);
        Assertions.assertNull(personService.approveFriendRequest(friendRequestDTO, friend.getPersonId()));
    }

    @Test
    void FriendRequestDoesNotExist(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList);

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setFriendId(loggedPerson.getPersonId());
        friendRequest.setFriendRequestId(1L);
        friendRequest.setDeleted(false);
        friendRequest.setStatus(FriendRequestStatus.PENDING);

        List<FriendRequest> friendRequestList = new ArrayList<>();
        friend.setFriendRequest(friendRequestList);

        FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
        friendRequestDTO.setFriendRequestId(friendRequest.getFriendRequestId());
        friendRequestDTO.setStatus(FriendRequestStatus.PENDING);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        when(friendRequestRepository.findByFriendRequestId(friendRequest.getFriendRequestId())).thenReturn(friendRequest);
        Assertions.assertNull(personService.approveFriendRequest(friendRequestDTO, friend.getPersonId()));
    }

    @Test
    void removeFriend(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        List<Person> friendList = new ArrayList<>();
        List<Person> friendList1 = new ArrayList<>();
        friendList.add(friend);
        friendList1.add(loggedPerson);
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList1);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        Assertions.assertNotNull(personService.removeFriend(loggedPerson.getPersonId(), friend.getPersonId()));
    }

    @Test
    void removeFriendWhereFriendshipDoesNotExist(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        List<Person> friendList = new ArrayList<>();
        loggedPerson.setFriends(friendList);
        friend.setFriends(friendList);

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        when(personRepository.findByPersonId(friend.getPersonId())).thenReturn(friend);
        Assertions.assertNull(personService.removeFriend(loggedPerson.getPersonId(), friend.getPersonId()));
    }

    @Test
    void removeFriendWhereOneOfIDsDoesNotExist(){
        Person loggedPerson = new Person();
        loggedPerson.setPersonId(1L);
        loggedPerson.setName("Stefan");

        Person friend = new Person();
        friend.setPersonId(2L);
        friend.setName("Ivan");

        when(personRepository.findByPersonId(loggedPerson.getPersonId())).thenReturn(loggedPerson);
        Assertions.assertNull(personService.removeFriend(loggedPerson.getPersonId(), friend.getPersonId()));
    }
}
