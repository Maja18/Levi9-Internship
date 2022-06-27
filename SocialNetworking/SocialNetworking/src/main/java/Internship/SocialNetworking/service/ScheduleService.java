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

    private final EventRepository eventRepository;

    private final NotificationServiceImpl notificationService;



    @Bean
    @Async
    @Scheduled(cron = "2 * * * * *")
    public void checkEventStartingTime(){
        if (eventService.getAllEvents().size() != 0) {
           List<Event> events = eventService.getAllEvents()
                    .stream()
                    .filter(event -> !event.getNotified())
                    .filter(e -> e.getStartEvent().toLocalDate().compareTo(LocalDate.now()) == 0)
                    .filter(e -> e.getStartEvent().toLocalTime().compareTo(LocalDateTime.now().toLocalTime().plusMinutes(60)) < 0)
                    .collect(Collectors.toList());
          // for(Event e : events){
          //     notificationService.sendNotificationsEvent(e);
           //}
            //System.out.println("Radi se!!");
           /* for(Event e : events){
                notificationService.sendNotificationsEvent(e);
            } */
            notificationService.iterateEventsList(events);
           // System.out.println(events.size());



            //System.out.println(events.size());

        }

    }

    @Bean
    @Async
    @Scheduled(cron = "1 * * * * *")
    public void checkIfEventFinished(){
        if(eventService.getAllEvents() != null){
            List<Event> events = eventService.getAllEvents().stream()
                    .filter(event -> !event.getIsOver())
                    .filter(event -> event.getEndEvent().toLocalTime().compareTo(LocalDateTime.now().toLocalTime()) < 0)
                    .collect(Collectors.toList());

            events.forEach(notificationService::checkIfEventIsOver);


        }




    }

}
