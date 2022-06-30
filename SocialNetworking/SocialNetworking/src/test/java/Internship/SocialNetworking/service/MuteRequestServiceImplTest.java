package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.MuteRequestDTO;
import Internship.SocialNetworking.mappers.MuteRequestMapper;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.MuteRequest;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.MuteRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import org.apache.tomcat.jni.Local;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class MuteRequestServiceImplTest {

    @Mock
    private  MuteRequestRepository muteRequestRepository;

    @Mock
    private  PersonRepository personRepository;

    private MuteRequestServiceImpl muteRequestService;

    @Spy
    MuteRequestMapper muteRequestMapper = Mappers.getMapper(MuteRequestMapper.class);

    @BeforeEach
    void setUp(){
        muteRequestService = new MuteRequestServiceImpl(muteRequestRepository, personRepository);
    }


    @Test
    void getAllMuteRequests() {
        muteRequestService.getAllMuteRequests();
        verify(muteRequestRepository).findAll();
    }

    @Test
    void checkIfSavesMuteRequest(){
        MuteRequest muteRequest = new MuteRequest();
        muteRequest.setMuteRequestId(12L);
        muteRequest.setPersonId(1L);
        muteRequestService.saveMuteRequest(muteRequest);

        assertEquals(muteRequestRepository.findByMuteRequestId(12L), muteRequestRepository.findByMuteRequestId(muteRequest.getMuteRequestId()));

    }

    @Test
    void checkIfMutingGroupsWorks(){
        Person person = new Person();
        person.setPersonId(12L);

        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);


        MuteRequestDTO muteRequestDTO = new MuteRequestDTO();
        muteRequestDTO.setGroupId(1L);
        muteRequestDTO.setMuteEnd("PERMANENT");

        List<MuteRequest> mutedGroups = new ArrayList<>();




        MuteRequest muteRequest = new MuteRequest();
        muteRequest.setGroupId(muteRequestDTO.getGroupId());
        muteRequest.setPersonId(person.getPersonId());
        muteRequest.setMuteStart(LocalDateTime.now());
        muteRequest.setMuteEnd(muteRequestDTO.getMuteEnd());
        mutedGroups.add(muteRequest);
        person.setMutedGroups(mutedGroups);

        when(muteRequestMapper.muteRequestToDto(muteRequest)).thenReturn(muteRequestDTO);

        Assertions.assertNotNull(muteRequestService.muteGroup(muteRequestDTO, person.getPersonId()));



    }

    @Test
    void checkIfTheGroupIsBlockedPermanently(){
        Person person = new Person();
        person.setPersonId(1L);

        MuteRequest muteRequest = new MuteRequest();
        muteRequest.setMuteRequestId(1L);
        muteRequest.setMuteEnd("PERMANENT");
        muteRequest.setGroupId(1L);
        List<MuteRequest> mutedGroups = new ArrayList<>();
        mutedGroups.add(muteRequest);

        person.setMutedGroups(mutedGroups);

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);

        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);


        Assertions.assertTrue(muteRequestService.isGroupBlockedPermanently(person.getPersonId(), groupNW.getGroupId()));


    }

    @Test
    void checkIfGroupIsNotBlockedPermanently(){
        Person person = new Person();
        person.setPersonId(1L);

        MuteRequest muteRequest = new MuteRequest();
        muteRequest.setMuteRequestId(1L);
        muteRequest.setMuteEnd("PERMANENT");
        muteRequest.setGroupId(2L);
        List<MuteRequest> mutedGroups = new ArrayList<>();
        mutedGroups.add(muteRequest);

        person.setMutedGroups(mutedGroups);

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);

        GroupNW groupNW1 = new GroupNW();
        groupNW1.setGroupId(2L);

        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);


        Assertions.assertFalse(muteRequestService.isGroupBlockedPermanently(person.getPersonId(), groupNW.getGroupId()));
    }

    @Test
    void checkIfGroupIsBlockedTemporary(){
        Person person = new Person();
        person.setPersonId(1L);

        MuteRequest muteRequest = new MuteRequest();
        muteRequest.setMuteRequestId(1L);
        muteRequest.setMuteEnd("2022-08-01T15:00:00");
        muteRequest.setGroupId(1L);
        List<MuteRequest> mutedGroups = new ArrayList<>();
        mutedGroups.add(muteRequest);

        person.setMutedGroups(mutedGroups);

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);

        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);

        Assertions.assertTrue(muteRequestService.isGroupBlockedTemporary(person.getPersonId(), groupNW.getGroupId()));
    }

    @Test
    void checkIfGroupIsNotBlockedTemporary(){
        Person person = new Person();
        person.setPersonId(1L);

        MuteRequest muteRequest = new MuteRequest();
        muteRequest.setMuteRequestId(1L);
        muteRequest.setMuteEnd("2022-08-01T15:00:00");
        muteRequest.setGroupId(2L);
        List<MuteRequest> mutedGroups = new ArrayList<>();
        mutedGroups.add(muteRequest);

        person.setMutedGroups(mutedGroups);

        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);

        GroupNW groupNW1 = new GroupNW();
        groupNW1.setGroupId(2L);

        when(personRepository.findByPersonId(person.getPersonId())).thenReturn(person);


        Assertions.assertFalse(muteRequestService.isGroupBlockedTemporary(person.getPersonId(), groupNW.getGroupId()));
    }

}