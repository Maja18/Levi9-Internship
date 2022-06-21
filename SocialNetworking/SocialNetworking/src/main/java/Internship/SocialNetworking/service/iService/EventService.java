package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.dto.EventDTO;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    public String createEvent(EventDTO eventDTO);
}
