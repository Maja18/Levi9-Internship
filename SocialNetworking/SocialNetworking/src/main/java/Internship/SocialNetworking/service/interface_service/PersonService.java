package Internship.SocialNetworking.service.interface_service;

import Internship.SocialNetworking.dto.FriendInfoDTO;
import Internship.SocialNetworking.dto.FriendRequestDTO;
import Internship.SocialNetworking.models.Person;


import Internship.SocialNetworking.dto.PersonDTO;


import java.util.List;

public interface PersonService {
    Person findByEmailEquals(String email);
    Person findByPersonId(Long id);
    FriendInfoDTO sendFriendRequest(Long personId, Long friendId);
    Person registerPerson(PersonDTO person);

    String addPersonToGroup(Long groupId,Long personId);

    String alterPersonInformation(PersonDTO person, Long userId);

    String deletePersonFromGroup(Long groupId,Long personId,Long administratorId);

    List<Person> getAllPersons();
    FriendInfoDTO removeFriend(Long personId, Long friendId);
    FriendInfoDTO approveFriendRequest(FriendRequestDTO friendRequestDTO, Long friendRequestId);
}
