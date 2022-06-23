package Internship.SocialNetworking.service;


import Internship.SocialNetworking.dto.NotificationDTO;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.PostMuteStatus;

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


import java.util.*;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;


    private final PersonRepository personRepository;

    private final PersonServiceImpl personService;
    private final GroupRepository groupRepository;

    private final EventRepository eventRepository;

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications(Long personId,Long groupId) {
        Person person = personRepository.findByPersonId(personId);
        GroupNW group=groupRepository.findByGroupId(groupId);
        List<Notification> listReceiverNotifications = new ArrayList<>();
        if(group!=null) {
            for(int i=0; i<group.getMembers().size(); i++) {
                if(personId == group.getMembers().get(i).getPersonId()) {
                    for(int p=0; p<person.getNotifications().size(); p++) {
                        if(Objects.equals(group.getName(), person.getNotifications().get(p).getGroupName())) {
                            if (Objects.equals(person.getNotifications().get(p).getReceiver(), person.getEmail())) {
                                Notification notification = person.getNotifications().get(p);
                                listReceiverNotifications.add(notification);
                            }
                        }
                    }
                   for(int k=0; k<listReceiverNotifications.size(); k++) {
                        if(listReceiverNotifications.get(k).getPostMuteStatus() == PostMuteStatus.PERMANENT) {
                            listReceiverNotifications.clear();
                           return listReceiverNotifications;
                        }
                    }
                    return listReceiverNotifications;
                }
            }
        }
        return null;
    }


    @Override
    public String muteNotification(Long groupId,Long authPersonId,Long muteStatus) {
        GroupNW group=groupRepository.findByGroupId(groupId);
        if(group != null) {
            for(int i=0; i<group.getMembers().size(); i++) {
                //we check whether is user member of group whose notifications want to mute
                if(authPersonId == group.getMembers().get(i).getPersonId()) {
                    Person person=personRepository.findByPersonId(authPersonId);
                    //we loop through notification list
                    //if mute status is 0 that means temporary,1 means permanent
                    for(int p=0; p<person.getNotifications().size(); p++) {
                        if (Objects.equals(person.getEmail(), person.getNotifications().get(p).getReceiver())) {
                            Notification notification = person.getNotifications().get(p);
                            if (muteStatus == 1) {
                                notification.setPostMuteStatus(PostMuteStatus.PERMANENT);
                                notificationRepository.save(notification);
                                return "Successfully turned notifications off temporary";
                            }

                        }

                    }
                }
            }
            return "You are not member of that group";
        }
        return null;
    }

    @Override

    public String addNotificationPost(String groupName, Person sender) {
        String content = "User " + sender.getUsername() + " shared a new post" +  " in group "; // + groupName;

        GroupNW group = groupRepository.findByNameEquals(groupName);
        List<Person> personList = group.getMembers().stream()
                .filter(user -> !user.getPersonId().equals(sender.getPersonId()))
                .collect(Collectors.toList());

        for(Person p : personList){
            Notification notification = new Notification();
            notification.setContent(content);
            notification.setSender(sender.getUsername());
            notification.setSource("Web notification");
            notification.setReceiver(p.getUsername());
            notification.setGroupName(groupName);
            notification.setPostMuteStatus(PostMuteStatus.NONE);
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
}
