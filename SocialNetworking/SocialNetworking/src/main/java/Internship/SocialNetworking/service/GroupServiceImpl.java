package Internship.SocialNetworking.service;


import Internship.SocialNetworking.dto.GroupDTO;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.EventRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.service.iService.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final PersonServiceImpl personService;

    private final EventRepository eventRepository;


    @Override
    public List<GroupNW> getAllGroups() {
        return groupRepository.findAll();
    }


    @Override
    public GroupNW getGroupById(Long id) {
        return groupRepository.findByGroupId(id);
    }

    @Override
    public void save(GroupNW groupNW) {
        groupRepository.save(groupNW);
    }

    @Override
    public String createGroup(GroupDTO groupDTO) {
        Optional<GroupNW> grNameCheck = Optional.ofNullable(getByName(groupDTO.getName()));
        if(grNameCheck.isPresent()){
            return "Group name already exists, make a new one!";
        }
        else {
            GroupNW groupNW = new GroupNW();
            groupNW.setCreatorId(groupDTO.getCreatorId());
            groupNW.setDescription(groupDTO.getDescription());
            groupNW.setPublic(groupDTO.getIsPublic());
            groupNW.setName(groupDTO.getName());
            save(groupNW);
            return "Group successfully made!";
        }


    }

    @Override
    public GroupNW getByName(String name) {
        return groupRepository.findByNameEquals(name);
    }

    @Override
    public boolean checkIfGroupMember(Long groupId, Long userId) {
        GroupNW group = groupRepository.findByGroupId(groupId);

        return group.getMembers().stream()
                .anyMatch(user -> user.getPersonId().equals(userId));
    }

    @Override
    public List<Event> groupEvents(Long groupId) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Optional<GroupNW> groupExists = Optional.ofNullable(groupRepository.findByGroupId(groupId));
        if(groupExists.isEmpty()){
            return null;
        }
        else if(!checkIfGroupMember(groupId, userWithId.getPersonId())){
            return null;
        }


        return eventRepository.findAll().stream()
                .filter(event -> event.getGroupId().equals(groupId)).collect(Collectors.toList());
    }
}
