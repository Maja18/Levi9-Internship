package Internship.SocialNetworking.service.interface_service;

import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.dto.GroupDTO;

import Internship.SocialNetworking.models.GroupNW;

import java.util.List;

public interface GroupService {
    List<GroupNW> getAllGroups();

    GroupNW getGroupById(Long id);

    GroupDTO createGroup(GroupDTO groupDTO, Long loggedUserId);

    GroupNW getByName(String name);

    void save(GroupNW groupNW);

    boolean checkIfGroupMember(Long groupId, Long userId);

    List<EventDTO> groupEvents(Long groupId, Long userId);



}
