package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.EventRepository;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.NotificationRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;


    private final PersonRepository personRepository;


    private final GroupRepository groupRepository;


    private final MuteRequestServiceImpl muteRequestService;

    private final EventRepository eventRepository;


    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public void addNotificationPost(String groupName, Person sender) {
        String content = "User " + sender.getUsername() + " shared a new post" +  " in group " + groupName;
        GroupNW group = groupRepository.findByNameEquals(groupName);
        List<Person> personList = group.getMembers().stream()
                .filter(user -> !user.getPersonId().equals(sender.getPersonId()))
                .filter(user -> !muteRequestService.isGroupBlockedPermanently(user.getPersonId(), group.getGroupId()))
                .filter(user -> !muteRequestService.isGroupBlockedTemporary(user.getPersonId(), group.getGroupId()))
                .collect(Collectors.toList());


        for(Person p : personList){
            Notification notification = new Notification();
            notification.setContent(content);
            notification.setSender(sender.getUsername());
            notification.setSource("Web notification");
            notification.setReceiver(p.getUsername());
            saveNotification(notification);
            p.getNotifications().add(notification);
            personRepository.save(p);

        }

    }

    @Override
    public void iterateEventsList(List<Event> events) {
        for(Event e : events){
            sendNotificationsEvent(e);
        }
    }

    @Override
    public void sendNotificationsEvent(Event event) {
        //Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        //Person loggedPerson = (Person) currentUser.getPrincipal();
        List<Person> persons = event.getGoing();
        String content = "The event you are going to " + event.getName()
                + " is starting soon : " + event.getStartEvent().toLocalTime().toString();
        //System.out.println(persons.size());

        for(Person p : persons){
            Notification notification = new Notification();
            notification.setContent(content);
            notification.setSource("Event scheduler");
            notification.setSender(personRepository.findByPersonId(event.getCreatorId()).getUsername());
            notification.setReceiver(p.getUsername());
            saveNotification(notification);
            Person temp = personRepository.findByPersonId(p.getPersonId());
            temp.getNotifications().add(notification);
            //p.getNotifications().add(notification);
            personRepository.save(temp);




            //System.out.println(notification.getNotificationId());

        }

        event.setNotified(true);
        eventRepository.save(event);
        System.out.println("zavrseno!!!!!!!!");
    }


    @Override
    public void checkIfEventIsOver(Event event) {
        List<Person> persons = event.getGoing();
        String content = "The event " + event.getName() +  "in group " +
                groupRepository.findByGroupId(event.getGroupId()).getName()
                + " is over!! " ;

        persons.forEach(person -> {
            Notification notification = new Notification();
            notification.setContent(content);
            notification.setSource("Event scheduler");
            notification.setSender(personRepository.findByPersonId(event.getCreatorId()).getUsername());
            notification.setReceiver(person.getUsername());
            saveNotification(notification);
            Person temp = personRepository.findByPersonId(person.getPersonId());
            temp.getNotifications().add(notification);
            personRepository.save(temp);
        });
        Event event1 = eventRepository.getByEventId(event.getEventId());
        event1.setIsOver(true);
        eventRepository.save(event1);


    }
}
