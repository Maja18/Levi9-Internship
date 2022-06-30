package Internship.SocialNetworking.repository;

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
        assertThat(muteRequestRepository.findByMuteRequestId(1L)).isNotNull();
    }

    @Test
    void checkIfFindByMuteRequestFalseIdWorks() {
        assertThat(muteRequestRepository.findByMuteRequestId(111L)).isNull();
    }




}