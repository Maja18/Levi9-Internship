package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.GroupRequest;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.RequestStatus;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.GroupRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        GroupNW group=new GroupNW();
        group.setGroupId(1L);
        group.setIsPublic(true);
        group.setCreatorId(4L);
        group.setDescription("Grupa 1");

        Person administrator=new Person();
        administrator.setPersonId(2L);
        administrator.setName("Milos");
        when(groupRequestRepository.findByGroupRequestId(groupRequest.getGroupRequestId())).thenReturn(groupRequest);
        when(personRepository.findByPersonId(administrator.getPersonId())).thenReturn(administrator);
        when(groupRepository.findByGroupId(group.getGroupId())).thenReturn(group);
        Assertions.assertNotNull(groupRequestService.acceptOrRejectRequest(groupRequest.getGroupRequestId(), administrator.getPersonId(),0L));
    }
}
