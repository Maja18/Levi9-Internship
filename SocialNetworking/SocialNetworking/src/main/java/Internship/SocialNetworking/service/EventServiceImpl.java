package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.EventRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;
    private final PersonRepository personRepository;

    private final GroupServiceImpl groupService;

    private final PersonServiceImpl personService;



    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public String goToEventOrNot(Long groupId, Long personId,Long eventId,Long presenceStatus) {
       GroupNW group=groupRepository.findByGroupId(groupId);
       if(group!=null) {
           for(int i=0; i<group.getMembers().size(); i++) {
                if(Objects.equals(personId, group.getMembers().get(i).getPersonId())) {
                    Event event=eventRepository.getByEventId(eventId);
                        if(event!=null) {
                            Person comingPerson=personRepository.findByPersonId(personId);
                            //if he inputs 0 that means he is going to an even otherwise he is not
                            if(presenceStatus == 0) {
                                event.getGoing().add(comingPerson);
                                personRepository.save(comingPerson);
                                return "Presence status confirmed!";
                            }
                            return "Not going";
                        }
                        return "No such event";
                }
           }
           return "Not a member";
       }
       return null;
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
        } else if(LocalDateTime.parse(eventDTO.getStartEvent()).compareTo(LocalDateTime.parse(eventDTO.getEndEvent())) > 0) {
            return "End date can't be before start date, please enter dates again!";
        }
        Event event = new Event();
        event.setCreatorId(userWithId.getPersonId());
        event.setName(eventDTO.getName());
        event.setX(eventDTO.getX());
        event.setY(eventDTO.getY());
        event.setStartEvent(LocalDateTime.parse(eventDTO.getStartEvent()));
        event.setEndEvent(LocalDateTime.parse(eventDTO.getEndEvent()));
        event.setGroupId(eventDTO.getGroupId());
        event.setIsOver(false);
        event.setNotified(false);
        eventRepository.save(event);

        return "Event created!!";
    }


}
