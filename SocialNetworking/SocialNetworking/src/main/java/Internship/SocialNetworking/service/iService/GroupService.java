package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.dto.GroupDTO;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;

import java.util.List;

public interface GroupService {
    List<GroupNW> getAllGroups();

    GroupNW getGroupById(Long id);

    String createGroup(GroupDTO groupDTO);

    GroupNW getByName(String name);

    void save(GroupNW groupNW);

    boolean checkIfGroupMember(Long groupId, Long userId);

    List<Event> groupEvents(Long groupId);



}
