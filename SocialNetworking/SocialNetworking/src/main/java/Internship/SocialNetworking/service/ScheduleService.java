package Internship.SocialNetworking.service;


import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableAsync
@Transactional
public class ScheduleService {

    private final EventServiceImpl eventService;


    private final NotificationServiceImpl notificationService;



    @Bean
    @Async
    @Scheduled(cron = "2 * * * * *")
    public void checkEventStartingTime(){
        List<Event> loadedEvents = eventService.getAllEvents();
        if (!loadedEvents.isEmpty()) {
           List<Event> events = loadedEvents
                    .stream()
                    .filter(event -> !event.getNotified())
                    .filter(e -> e.getStartEvent().toLocalDate().compareTo(LocalDate.now()) == 0)
                    .filter(e -> e.getStartEvent().toLocalTime().compareTo(LocalDateTime.now().toLocalTime().plusMinutes(60)) < 0)
                    .collect(Collectors.toList());

            notificationService.iterateEventsList(events);

        }

    }

    @Bean
    @Async
    @Scheduled(cron = "1 * * * * *")
    public void checkIfEventFinished(){
        List<Event> loadedEvents = eventService.getAllEvents();
        if(!loadedEvents.isEmpty()){
            List<Event> events = loadedEvents
                    .stream()
                    .filter(event -> !event.getIsOver())
                    .filter(event -> event.getEndEvent().toLocalDate().compareTo(LocalDate.now()) == 0)
                    .filter(event -> event.getEndEvent().toLocalTime().compareTo(LocalDateTime.now().toLocalTime()) < 0)
                    .collect(Collectors.toList());

            events.forEach(notificationService::checkIfEventIsOver);


        }




    }

}
