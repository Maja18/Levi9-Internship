package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Notification findByNotificationId(Long notificationId);

}
