package Internship.SocialNetworking.service.interface_service;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;

import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> getAllNotifications();

    void addNotificationPost(String groupName, Person sender);

    void sendNotificationsEvent(Event event);

    void iterateEventsList(List<Event> events);

    void checkIfEventIsOver(Event event);

    void updateEventToOver(Long id);

    void updateEventToNotified(Long id);

}
