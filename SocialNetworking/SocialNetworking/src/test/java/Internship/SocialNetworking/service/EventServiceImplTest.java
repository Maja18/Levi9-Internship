package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.exceptions.EventException;
import Internship.SocialNetworking.exceptions.GroupException;
import Internship.SocialNetworking.mappers.EventMapper;
import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.EventRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private  EventRepository eventRepository;
    @Mock
    private  GroupRepository groupRepository;
    @Mock
    private  PersonRepository personRepository;

    @Mock
    private  GroupServiceImpl groupService;



    private EventServiceImpl eventService;

    @Spy
    EventMapper eventMapper = Mappers.getMapper(EventMapper.class);

    @BeforeEach
    void setUp(){
        eventService = new EventServiceImpl(eventRepository, groupRepository, personRepository, groupService);
    }

    @Test
    void checkIfGetAllEventsWork(){
        eventService.getAllEvents();
        verify(eventRepository).findAll();
    }

    @Test
    void checkIfCreateEventWorks(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(10L);

        Event event = new Event();
        event.setEventId(10L);


        EventDTO eventDTO = new EventDTO();
        eventDTO.setGroupId(10L);
        eventDTO.setName("Event2");
        eventDTO.setStartEvent("2022-07-11T15:00:00");
        eventDTO.setEndEvent("2022-07-21T15:00:00");

        Person person = new Person();
        person.setPersonId(11L);

        List<Person> members = new ArrayList<>();
        members.add(person);
        groupNW.setMembers(members);


        when(groupService.getGroupById(eventDTO.getGroupId())).thenReturn(groupNW);
        when(eventRepository.getByName(eventDTO.getName())).thenReturn(null);

        when(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId())).thenReturn(true);
        assertTrue(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId()));

        assertEquals(-10, LocalDateTime.parse(eventDTO.getStartEvent()).compareTo(LocalDateTime.parse(eventDTO.getEndEvent())));

        when(eventMapper.dtoToEvent(eventDTO)).thenReturn(event);
        when(eventMapper.eventToDto(event)).thenReturn(eventDTO);

        Assertions.assertNotNull(eventService.createEvent(eventDTO, person.getPersonId()));
    }

    @Test
    void checkIfCreateEventFailsWithNonExistingGroup(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(10L);

        Event event = new Event();
        event.setEventId(10L);


        EventDTO eventDTO = new EventDTO();
        eventDTO.setGroupId(10L);
        eventDTO.setName("Event2");
        eventDTO.setStartEvent("2022-07-11T15:00:00");
        eventDTO.setEndEvent("2022-07-21T15:00:00");

        Person person = new Person();
        person.setPersonId(11L);

        List<Person> members = new ArrayList<>();
        members.add(person);
        groupNW.setMembers(members);

        when(groupService.getGroupById(eventDTO.getGroupId())).thenReturn(null);
        when(eventRepository.getByName(eventDTO.getName())).thenReturn(null);

        when(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId())).thenReturn(true);
        assertTrue(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId()));

        assertEquals(-10, LocalDateTime.parse(eventDTO.getStartEvent()).compareTo(LocalDateTime.parse(eventDTO.getEndEvent())));

        GroupException exception = Assertions.assertThrows(GroupException.class, () ->
                eventService.createEvent(eventDTO, person.getPersonId()));
        Assertions.assertEquals("That group does not exists!", exception.getMessage());

    }


    @Test
    void checkIfEventIsNotCreatedWithExistingName(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(10L);

        Event event = new Event();
        event.setEventId(10L);
        event.setName("Event2");


        EventDTO eventDTO = new EventDTO();
        eventDTO.setGroupId(10L);
        eventDTO.setName("Event2");
        eventDTO.setStartEvent("2022-07-11T15:00:00");
        eventDTO.setEndEvent("2022-07-21T15:00:00");

        Person person = new Person();
        person.setPersonId(11L);

        List<Person> members = new ArrayList<>();
        members.add(person);
        groupNW.setMembers(members);

        when(groupService.getGroupById(eventDTO.getGroupId())).thenReturn(groupNW);
        when(eventRepository.getByName(eventDTO.getName())).thenReturn(event);

        when(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId())).thenReturn(true);
        assertTrue(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId()));

        assertEquals(-10, LocalDateTime.parse(eventDTO.getStartEvent()).compareTo(LocalDateTime.parse(eventDTO.getEndEvent())));

        EventException exception = Assertions.assertThrows(EventException.class, () ->
                eventService.createEvent(eventDTO, person.getPersonId()));
        Assertions.assertEquals("An event with that name already exists!", exception.getMessage());
    }

    @Test
    void checkIfEventCreatingIsImpossibleIfNotGroupMember(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(10L);

        Event event = new Event();
        event.setEventId(10L);
        event.setName("Event2");


        EventDTO eventDTO = new EventDTO();
        eventDTO.setGroupId(10L);
        eventDTO.setName("Event2");
        eventDTO.setStartEvent("2022-07-11T15:00:00");
        eventDTO.setEndEvent("2022-07-21T15:00:00");

        Person person = new Person();
        person.setPersonId(11L);

        Person person1 = new Person();
        person.setPersonId(12L);

        List<Person> members = new ArrayList<>();
        members.add(person1);
        groupNW.setMembers(members);

        when(groupService.getGroupById(eventDTO.getGroupId())).thenReturn(groupNW);
        when(eventRepository.getByName(eventDTO.getName())).thenReturn(null);

        when(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId())).thenReturn(false);
        assertFalse(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId()));

        assertEquals(-10, LocalDateTime.parse(eventDTO.getStartEvent()).compareTo(LocalDateTime.parse(eventDTO.getEndEvent())));

        GroupException exception = Assertions.assertThrows(GroupException.class, () ->
                eventService.createEvent(eventDTO, person.getPersonId()));
        Assertions.assertEquals("User is not group member!", exception.getMessage());

    }

    @Test
    void checkIfEventCreatingIsImpossibleWithEndDateBeforeStartDate(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(10L);

        Event event = new Event();
        event.setEventId(10L);


        EventDTO eventDTO = new EventDTO();
        eventDTO.setGroupId(10L);
        eventDTO.setName("Event2");
        eventDTO.setStartEvent("2022-07-11T15:00:00");
        eventDTO.setEndEvent("2022-07-01T15:00:00");

        Person person = new Person();
        person.setPersonId(11L);

        List<Person> members = new ArrayList<>();
        members.add(person);
        groupNW.setMembers(members);


        when(groupService.getGroupById(eventDTO.getGroupId())).thenReturn(groupNW);
        when(eventRepository.getByName(eventDTO.getName())).thenReturn(null);

        when(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId())).thenReturn(true);
        assertTrue(groupService.checkIfGroupMember(groupNW.getGroupId(), person.getPersonId()));

        assertNotEquals(-10, LocalDateTime.parse(eventDTO.getStartEvent()).compareTo(LocalDateTime.parse(eventDTO.getEndEvent())));

        EventException exception = Assertions.assertThrows(EventException.class, () ->
                eventService.createEvent(eventDTO, person.getPersonId()));
        Assertions.assertEquals("Event end date can't be before start date!", exception.getMessage());

    }





}