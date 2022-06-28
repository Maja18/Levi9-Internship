package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.dto.GroupDTO;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;

import java.util.List;

public interface GroupService {
    List<GroupNW> getAllGroups();

    GroupNW getGroupById(Long id);

    GroupDTO createGroup(GroupDTO groupDTO);

    GroupNW getByName(String name);

    void save(GroupNW groupNW);

    boolean checkIfGroupMember(Long groupId, Long userId);

    List<EventDTO> groupEvents(Long groupId);



}
