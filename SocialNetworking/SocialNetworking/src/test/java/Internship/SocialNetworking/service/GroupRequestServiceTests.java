package Internship.SocialNetworking.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GroupRequestServiceTests {

    @Autowired
    private GroupRequestServiceImpl groupRequestService;


    @Test
    void listAdminRequests() {

        Assertions.assertNotNull(groupRequestService.listAllRequests(1L));
        Assertions.assertNotEquals(6,groupRequestService.listAllRequests(1L).size());

    }

    @Test
    void acceptOrRejectRequest() {
        Assertions.assertNotNull(groupRequestService.acceptOrRejectRequest(1L,1L,0L));
    }
}
