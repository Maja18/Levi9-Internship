package Internship.SocialNetworking.mappers;

import Internship.SocialNetworking.dto.EventDTO;
import Internship.SocialNetworking.models.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);


    EventDTO eventToDto(Event event);


    List<EventDTO> eventsToDto(List<Event> events);


    Event dtoToEvent(EventDTO eventDTO);

}
