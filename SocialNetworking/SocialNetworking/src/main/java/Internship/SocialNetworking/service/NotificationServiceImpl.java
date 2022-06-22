package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.NotificationDTO;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.PostMuteStatus;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.NotificationRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final GroupServiceImpl groupService;

    private final PersonRepository personRepository;


    private final GroupRepository groupRepository;

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications(String personName) {
        List<Notification> notificationList=notificationRepository.findAll();
        //new list that we will add receiver notifications in
        List<Notification> listReceiverNotifications=new ArrayList<>();
        System.out.println(notificationList.get(0).getReceiver());
        System.out.println(personName);
        for(int i=0; i<notificationList.size(); i++) {
            if(Objects.equals(personName, notificationList.get(i).getReceiver())) {
                    Notification notification = notificationList.get(i);
                    listReceiverNotifications.add(notification);

            }
        }
        for(int i=0; i<listReceiverNotifications.size(); i++) {
            //if user has turned notifications off,he won't receive anything
            if(listReceiverNotifications.get(i).getPostMuteStatus()==PostMuteStatus.PERMANENT)
                listReceiverNotifications.clear();
        }
        return listReceiverNotifications;
    }


    @Override
    public String muteNotification(NotificationDTO notification) {
        Notification notifyAlert=notificationRepository.findByNotificationId(notification.getNotificationId());
        if(notifyAlert!=null) {
            Notification modifiedNotification=new Notification();
            modifiedNotification.setNotificationId(notification.getNotificationId());
            modifiedNotification.setContent(notification.getContent());
            modifiedNotification.setSource(notification.getSource());
            modifiedNotification.setSender(notification.getSender());
            modifiedNotification.setReceiver(notification.getReceiver());
            modifiedNotification.setPostMuteStatus(notification.getPostMuteStatus());
            if(modifiedNotification.getPostMuteStatus()==PostMuteStatus.PERMANENT) {
                notificationRepository.save(modifiedNotification);
                return "You have successfully turned notifications off permanently!";
            }
        }
        return null;
    }

    @Override
    public String addNotificationPost(String groupName, Person sender) {
        String content = "User " + sender.getUsername() + " shared a new post" +  " in group " + groupName;
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
            notification.setPostMuteStatus(PostMuteStatus.NONE);
            saveNotification(notification);
            p.getNotifications().add(notification);
            personRepository.save(p);

        }

        return "Notification with content " + content + "has been sent to all group members!";
    }
}
