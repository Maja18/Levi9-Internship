package Internship.SocialNetworking.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EventRepositoryTest {

    @Resource
    private EventRepository eventRepository;


    @Test
    void checkIfReturnsEventById(){
        assertThat(eventRepository.getByEventId(2L)).isNotNull();
    }

    @Test
    void checkIfReturnsNullForFalseEventId(){
        assertThat(eventRepository.getByEventId(273L)).isNull();
    }

    @Test
    void checkIfGetByEventNameWorks(){
        assertThat(eventRepository.getByName("Event1")).isNotNull();
    }

    @Test
    void checkIfGetByFalseEventNameWorks(){
        assertThat(eventRepository.getByName("Event 12345")).isNull();
    }


}