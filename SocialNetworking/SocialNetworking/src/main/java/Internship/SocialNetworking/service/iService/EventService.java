package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.dto.EventDTO;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

     String goToEventOrNot(Long groupId,Long personId,Long eventId,Long presenceStatus);
     EventDTO createEvent(EventDTO eventDTO, Long creatorId);
}
