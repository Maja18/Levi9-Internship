package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;

import java.util.List;

public interface NotificationService {

    void saveNotification(Notification notification);

    List<Notification> getAllNotifications();

    String addNotificationPost(String groupName, Person sender);

}
