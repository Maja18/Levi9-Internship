package Internship.SocialNetworking.service;

import Internship.SocialNetworking.exceptions.EventException;
import Internship.SocialNetworking.exceptions.GroupException;
import Internship.SocialNetworking.exceptions.PersonException;
import Internship.SocialNetworking.mappers.EventMapper;
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
    public EventDTO createEvent(EventDTO eventDTO, Long creatorId) {
        Optional<GroupNW> groupExists = Optional.ofNullable(groupService.getGroupById(eventDTO.getGroupId()));
        Optional<Event> eventExists = Optional.ofNullable(eventRepository.getByName(eventDTO.getName()));


        boolean isMember = groupService.checkIfGroupMember(eventDTO.getGroupId(), creatorId);
        int checkDate = LocalDateTime.parse(eventDTO.getStartEvent()).compareTo(LocalDateTime.parse(eventDTO.getEndEvent()));

        if(groupExists.isEmpty()){
            throw new GroupException("That group does not exists!");
        }
        else if(eventExists.isPresent()){
            throw new EventException("An event with that name already exists!");
        }
        else if(!isMember){
            throw new GroupException("User is not group member!");
        } else if(checkDate > 0) {
            throw new EventException("Event end date can't be before start date!");
        }
        Event event = EventMapper.INSTANCE.dtoToEvent(eventDTO);

        event.setCreatorId(creatorId);
        event.setIsOver(false);
        event.setNotified(false);
        eventRepository.save(event);

        return EventMapper.INSTANCE.eventToDto(event);
    }


}
