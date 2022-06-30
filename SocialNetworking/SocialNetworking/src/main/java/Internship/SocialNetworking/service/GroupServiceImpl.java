package Internship.SocialNetworking.service;


import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.dto.GroupDTO;


import Internship.SocialNetworking.exceptions.GroupException;
import Internship.SocialNetworking.exceptions.PersonException;
import Internship.SocialNetworking.mappers.EventMapper;
import Internship.SocialNetworking.mappers.GroupMapper;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.EventRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.service.interface_service.GroupService;
import lombok.RequiredArgsConstructor;

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
    public GroupDTO createGroup(GroupDTO groupDTO, Long loggedUserId) {
        Optional<GroupNW> grNameCheck = Optional.ofNullable(getByName(groupDTO.getName()));
        Optional<Person> personExists = Optional.ofNullable(personService.findByPersonId(loggedUserId));
        if(grNameCheck.isPresent()){
            throw new GroupException("A group with that name already exist, choose a new one!");
        }
        else if(personExists.isEmpty())
        {
            throw new PersonException("That person does not exists!");
        }
        else {
            GroupNW groupNW = GroupMapper.INSTANCE.dtoToGroup(groupDTO);
            groupNW.setCreatorId(loggedUserId);
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
    public List<EventDTO> groupEvents(Long groupId, Long userId) {
        Optional<GroupNW> groupExists = Optional.ofNullable(groupRepository.findByGroupId(groupId));
        if(groupExists.isEmpty()){
            throw new GroupException("That group does not exists!");
        }
        else if(!checkIfGroupMember(groupId, userId)){
            throw new GroupException("You are not member of this group!");
        }
        List<Event> events = eventRepository.findAll().stream()
                .filter(event -> event.getGroupId().equals(groupId))
                .filter(event -> !event.getIsOver())
                .collect(Collectors.toList());

        return EventMapper.INSTANCE.eventsToDto(events);
    }
}
