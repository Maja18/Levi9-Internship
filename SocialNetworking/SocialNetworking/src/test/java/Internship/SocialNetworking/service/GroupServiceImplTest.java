package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.dto.GroupDTO;
import Internship.SocialNetworking.exceptions.GroupException;
import Internship.SocialNetworking.exceptions.PersonException;
import Internship.SocialNetworking.mappers.EventMapper;
import Internship.SocialNetworking.mappers.GroupMapper;
import Internship.SocialNetworking.mappers.PersonMapper;
import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.EventRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private PersonRepository personRepository;
    private GroupServiceImpl groupService;

    @Mock
    private PersonServiceImpl personService;

    @Mock
    private EventRepository eventRepository;


    @Spy
    GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);

    @Spy
    EventMapper eventMapper = Mappers.getMapper(EventMapper.class);


    @BeforeEach
    void setUp(){

        groupService = new GroupServiceImpl(groupRepository, personService, eventRepository);
    }

    @Test
    void testMethodReturningAllGroups()
    {
        //when
        groupService.getAllGroups();
        //then
        verify(groupRepository).findAll();

    }



    @Test
    void checkIfMethodGetsGroupById(){
        groupService.getGroupById(1L);
        verify(groupRepository).findByGroupId(1L);

    }

    @Test
    void checkIfGroupDoesNotExistById(){
        assertThat(groupService.getGroupById(289L)).isNull();
    }

    @Test
    void checkIfSaveMethodWorks(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(29L);
        groupNW.setCreatorId(1L);
        groupNW.setDescription("Something");
        groupNW.setIsPublic(true);
        groupNW.setName("group n eka");
        groupService.save(groupNW);
        assertEquals(groupService.getGroupById(groupNW.getGroupId()), groupService.getGroupById(29L));

    }

    @Test
    void checkIfCreateGroupWorks(){
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setDescription("Ovo je neka grupa");
        groupDTO.setName("Ime grupe");
        groupDTO.setPublic(true);

        GroupNW group1 = new GroupNW();
        group1.setDescription("Ovo je neka grupa");
        group1.setName("Ime grupe");
        group1.setIsPublic(true);

        Person person = new Person();
        person.setPersonId(3L);


        when(groupService.getByName(groupDTO.getName())).thenReturn(null);
        when(personService.findByPersonId(person.getPersonId())).thenReturn(person);
        when(groupMapper.dtoToGroup(groupDTO)).thenReturn(group1);

        assertThat(groupDTO.getName()).isNotNull();
        assertThat(personService.findByPersonId(person.getPersonId())).isNotNull();


        Assertions.assertNotNull(groupService.createGroup(groupDTO, person.getPersonId()));
    }

    @Test
    void checkIfGroupCreatingWithExistingNameIsNotPossible(){
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setDescription("Ovo je neka grupa");
        groupDTO.setName("Graphical");
        groupDTO.setPublic(true);

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);
        groupNW.setName("Graphical");

        Person person = new Person();
        person.setPersonId(1L);

        when(groupService.getByName(groupDTO.getName())).thenReturn(groupNW);
        when(personService.findByPersonId(person.getPersonId())).thenReturn(person);

        GroupException exception = Assertions.assertThrows(GroupException.class, ()->
                groupService.createGroup(groupDTO, 1L));
        Assertions.assertEquals("A group with that name already exist, choose a new one!", exception.getMessage());


    }

    @Test
    void checkIfGroupCreatingWithNonExistingUserIsImpossible(){
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setDescription("Ovo je neka grupa");
        groupDTO.setName("Graphical");
        groupDTO.setPublic(true);

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);
        groupNW.setName("Graphical");

        Person person = new Person();
        person.setPersonId(1L);

        when(groupService.getByName(groupDTO.getName())).thenReturn(null);
        when(personService.findByPersonId(person.getPersonId())).thenReturn(null);


        PersonException exception = Assertions.assertThrows(PersonException.class, ()->
                groupService.createGroup(groupDTO, 1L));
        Assertions.assertEquals("That person does not exists!", exception.getMessage());

    }

    @Test
    void checkIfGetByNameMethodWorks(){
        groupService.getByName("Group 1");
        verify(groupRepository).findByNameEquals("Group 1");
    }

    @Test
    void checkIfGroupByGivenNameDoesNotExists(){
        assertThat(groupService.getByName("asd-asd")).isNull();
    }

    @Test
    void checkIfPersonIsGroupMember(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(12L);
        Person person = new Person();
        person.setPersonId(12L);
        List<Person> members = new ArrayList<>();
        members.add(person);
        groupNW.setMembers(members);

        when(groupRepository.findByGroupId(12L)).thenReturn(groupNW);
        assertTrue(groupService.checkIfGroupMember(12L, 12L));


    }

    @Test
    void checkIfReturningGroupEventsWorksFine(){

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(5L);
        groupNW.setName("Group 5");;
        groupNW.setDescription("LA");

        when(groupRepository.findByGroupId(groupNW.getGroupId())).thenReturn(groupNW);


        Person person = new Person();
        person.setPersonId(12L);
        List<Person> members = new ArrayList<>();
        members.add(person);
        groupNW.setMembers(members);


        when(groupRepository.findByGroupId(groupNW.getGroupId())).thenReturn(groupNW);
        assertTrue(groupService.checkIfGroupMember(5L, 12L));

        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.setEventId(4L);
        event.setGroupId(5L);
        event.setIsOver(false);
        events.add(event);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setGroupId(5L);
        List<EventDTO> eventDTOS = new ArrayList<>();
        eventDTOS.add(eventDTO);

        when(eventRepository.findAll()).thenReturn(events);
        when(eventMapper.eventsToDto(events)).thenReturn(eventDTOS);
        Assertions.assertEquals(1,
                groupService.groupEvents(groupNW.getGroupId(), person.getPersonId()).size());



    }

    @Test
    void checkGroupEventsIfGroupDoesNotExists(){

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(225L);
        groupNW.setName("Group 225");;
        groupNW.setDescription("LA");

        Person person = new Person();
        person.setPersonId(1L);

        List<Person> members = new ArrayList<>();
        members.add(person);
        groupNW.setMembers(members);

        when(groupRepository.findByGroupId(groupNW.getGroupId())).thenReturn(null);

        GroupException exception = Assertions.assertThrows(GroupException.class, ()-> {
            groupService.groupEvents(225L, 1L);
        });
        Assertions.assertEquals("That group does not exists!", exception.getMessage());

    }

    @Test
    void checkGroupEventsIfPersonIsNotGroupMember(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(225L);
        groupNW.setName("Group 225");
        groupNW.setDescription("LA");

        Person person = new Person();
        person.setPersonId(1L);

        Person person1 = new Person();
        person1.setPersonId(2L);

        List<Person> members = new ArrayList<>();
        members.add(person1);
        groupNW.setMembers(members);

       when(groupRepository.findByGroupId(groupNW.getGroupId())).thenReturn(groupNW);
       when(groupRepository.findByGroupId(groupNW.getGroupId())).thenReturn(groupNW);
      // when(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId())).thenReturn(false);
       assertFalse(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId()));


        GroupException exception = Assertions.assertThrows(GroupException.class, ()-> {
            groupService.groupEvents(225L, 1L);
        });
        Assertions.assertEquals("You are not member of this group!", exception.getMessage());

    }


}