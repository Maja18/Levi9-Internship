package Internship.SocialNetworking.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long notificationId;


    @Column
    private String content;

    @Column
    private String source;

    @Column
    private String sender;

    @Column
    private String receiver;



}
