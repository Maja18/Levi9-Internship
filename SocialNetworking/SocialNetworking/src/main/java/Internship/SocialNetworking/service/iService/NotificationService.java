package Internship.SocialNetworking.service.iService;


import Internship.SocialNetworking.dto.NotificationDTO;

import Internship.SocialNetworking.models.Event;

import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;

import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> getAllNotifications(Long loggedPersonUserName,Long groupId);

    String muteNotification(Long groupId,Long authPersonId,Long muteStatus);

    void addNotificationPost(String groupName, Person sender);

    void sendNotificationsEvent(Event event);

    void iterateEventsList(List<Event> events);

}
