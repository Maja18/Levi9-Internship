package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Event;
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
       Event event = new Event();
       event.setEventId(1L);
       event.setName("Pri event");
       event.setGroupId(1L);
       eventRepository.save(event);
       Event e = eventRepository.getByEventId(1L);
       assertThat(e.getEventId()).isEqualTo(1L);

    }

    @Test
    void checkIfReturnsNullForFalseEventId(){
        Event event = new Event();
        event.setEventId(1L);
        event.setName("Pri event");
        event.setGroupId(1L);
        eventRepository.save(event);
        Event e = eventRepository.getByEventId(111L);
        assertThat(e).isNull();

    }

    @Test
    void checkIfGetByEventNameWorks(){

        Event event = new Event();
        event.setEventId(1L);
        event.setName("Pri event");
        event.setGroupId(1L);
        eventRepository.save(event);
        Event e = eventRepository.getByName("Pri event");
        assertThat(e.getName()).isEqualTo("Pri event");
    }

    @Test
    void checkIfGetByFalseEventNameWorks(){
        assertThat(eventRepository.getByName("Event 12345")).isNull();
    }


}