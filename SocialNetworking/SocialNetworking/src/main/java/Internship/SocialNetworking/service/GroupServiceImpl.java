package Internship.SocialNetworking.service;


import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.dto.GroupDTO;

import Internship.SocialNetworking.mapper.EventMapper;
import Internship.SocialNetworking.mapper.EventMapperImpl;
import Internship.SocialNetworking.mapper.GroupMapper;
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
    public GroupDTO createGroup(GroupDTO groupDTO) {
        Optional<GroupNW> grNameCheck = Optional.ofNullable(getByName(groupDTO.getName()));
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        if(grNameCheck.isPresent()){
            return null;
        }
        else {
            GroupNW groupNW = GroupMapper.INSTANCE.dtoToGroup(groupDTO);
            groupNW.setCreatorId(userWithId.getPersonId());
            /*groupNW.setDescription(groupDTO.getDescription());
            groupNW.setIsPublic(groupDTO.getIsPublic());
            groupNW.setName(groupDTO.getName());*/
            
            save(groupNW);
            return groupDTO;
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
    public List<EventDTO> groupEvents(Long groupId) {
        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        Optional<GroupNW> groupExists = Optional.ofNullable(groupRepository.findByGroupId(groupId));
        if(groupExists.isEmpty()){
            return null;
        }
        else if(!checkIfGroupMember(groupId, userWithId.getPersonId())){
            return null;
        }



        List<Event> events = eventRepository.findAll().stream()
                .filter(event -> event.getGroupId().equals(groupId))
                .filter(event -> !event.getIsOver())
                .collect(Collectors.toList());

        return EventMapper.INSTANCE.eventsToDto(events);
    }
}
