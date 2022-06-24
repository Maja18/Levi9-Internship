package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Notification;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.NotificationRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final GroupServiceImpl groupService;

    private final PersonRepository personRepository;


    private final GroupRepository groupRepository;

    private final MuteRequestServiceImpl muteRequestService;

    @Override
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public String addNotificationPost(String groupName, Person sender) {
        String content = "User " + sender.getUsername() + " shared a new post" +  " in group " + groupName;
        GroupNW group = groupRepository.findByNameEquals(groupName);
        List<Person> personList = group.getMembers().stream()
                .filter(user -> !user.getPersonId().equals(sender.getPersonId()))
                .filter(user -> !muteRequestService.isGroupBlockedPermanently(user.getPersonId(), group.getGroupId()) )
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

        return "Notification with content " + content + "has been sent to all group members!";
    }
}
