package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.GroupRequest;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.RequestStatus;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.GroupRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class GroupRequestServiceTests {

    @InjectMocks
    private GroupRequestServiceImpl groupRequestService;

    @Mock
    private GroupRequestRepository groupRequestRepository;

    @Mock
    private PersonRepository personRepository;
    @Mock
    private GroupRepository groupRepository;
    @Before
    void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void listAdminRequests() {
        Person administrator=new Person();
        administrator.setPersonId(1L);
        administrator.setName("Nikola");
        when(personRepository.findByPersonId(administrator.getPersonId())).thenReturn(administrator);
        Assertions.assertNotNull(groupRequestService.listAllRequests(administrator.getPersonId()));
        Assertions.assertNotEquals(6,groupRequestService.listAllRequests(1L).size());

    }

    @Test
    void acceptOrRejectRequest() {
        GroupRequest groupRequest=new GroupRequest();
        groupRequest.setGroupId(1L);
        groupRequest.setGroupRequestId(1L);
        groupRequest.setCreatorId(4L);
        groupRequest.setRequestStatus(RequestStatus.PENDING);

        GroupRequest secondGroupRequest=new GroupRequest();
        secondGroupRequest.setGroupId(5L);
        secondGroupRequest.setGroupRequestId(3L);
        secondGroupRequest.setCreatorId(1L);
        secondGroupRequest.setRequestStatus(RequestStatus.PENDING);


        GroupNW group=new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
        group.setCreatorId(4L);
        group.setDescription("Grupa 1");

        GroupNW secondGroup=new GroupNW();
        secondGroup.setGroupId(5L);
        secondGroup.setIsPublic(true);
        secondGroup.setCreatorId(2L);
        secondGroup.setDescription("Group 5");

        Person administrator=new Person();
        administrator.setPersonId(2L);
        administrator.setName("Milos");

        Person secondAdministrator=new Person();
        administrator.setPersonId(8L);
        administrator.setName("Nikola");
        when(groupRequestRepository.findByGroupRequestId(groupRequest.getGroupRequestId())).thenReturn(groupRequest);
        when(groupRequestRepository.findByGroupRequestId(secondGroupRequest.getGroupRequestId())).thenReturn(secondGroupRequest);
        when(personRepository.findByPersonId(administrator.getPersonId())).thenReturn(administrator);
        when(personRepository.findByPersonId(secondAdministrator.getPersonId())).thenReturn(secondAdministrator);
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(group);
        when(groupRepository.findByGroupId(secondGroup.getGroupId())).thenReturn(secondGroup);
        Assertions.assertNotNull(groupRequestService.acceptOrRejectRequest(groupRequest.getGroupRequestId(), administrator.getPersonId(),0L));
        Assertions.assertNotNull(groupRequestService.acceptOrRejectRequest(groupRequest.getGroupRequestId(), administrator.getPersonId(),1L));
        Assertions.assertNotNull(groupRequestService.acceptOrRejectRequest(secondGroupRequest.getGroupRequestId(), secondAdministrator.getPersonId(),1L));
        Assertions.assertNotNull(groupRequestService.acceptOrRejectRequest(secondGroupRequest.getGroupRequestId(), secondAdministrator.getPersonId(),0L));
    }
}
