package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.MuteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MuteRequestRepositoryTest {

    @Resource
    private MuteRequestRepository muteRequestRepository;

    @Test
    void checkIfFindByMuteRequestIdWorks() {

        MuteRequest muteRequest = new MuteRequest();
       muteRequest.setMuteRequestId(1L);
       muteRequestRepository.save(muteRequest);

       MuteRequest m1 = muteRequestRepository.findByMuteRequestId(1L);
       assertThat(m1.getMuteRequestId()).isEqualTo(1L);

    }

    @Test
    void checkIfFindByMuteRequestFalseIdWorks() {
        assertThat(muteRequestRepository.findByMuteRequestId(111L)).isNull();
    }




}