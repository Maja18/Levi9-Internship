package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.EventRepository;
import Internship.SocialNetworking.service.iService.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final GroupServiceImpl groupService;

    private final PersonServiceImpl personService;



    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public String createEvent(EventDTO eventDTO) {
        Optional<GroupNW> groupExists = Optional.ofNullable(groupService.getGroupById(eventDTO.getGroupId()));
        Optional<Event> eventExists = Optional.ofNullable(eventRepository.getByName(eventDTO.getName()));

        Person currentUser = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person userWithId = personService.findByPersonId(currentUser.getPersonId());
        boolean isMember = groupService.checkIfGroupMember(eventDTO.getGroupId(), userWithId.getPersonId());

        if(groupExists.isEmpty()){
            return "That group does not exist, please try again";
        }
        else if(eventExists.isPresent()){
            return "Event with that name already exists, please enter new one";
        }
        else if(!isMember){
            return "User must be a group member to make an event";
        }
        Event event = new Event();
        event.setCreatorId(userWithId.getPersonId());
        event.setName(eventDTO.getName());
        event.setX(eventDTO.getX());
        event.setY(eventDTO.getY());
        event.setStartEvent(LocalDateTime.parse(eventDTO.getStartEvent()));
        event.setEndEvent(LocalDateTime.parse(eventDTO.getEndEvent()));
        event.setGroupId(eventDTO.getGroupId());
        eventRepository.save(event);

        return "Event created!!";
    }
}
