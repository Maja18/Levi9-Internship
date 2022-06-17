package Internship.SocialNetworking.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Event {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "event_id", nullable = false)
   private Long eventId;

   @Column
   private Date startEvent;

   @Column
   private Date endEvent;

   @Column
   private float location;

   @Column
   private Long groupId;

   @Column
   private Long creatorId;

   @Column
   private boolean isOver;

}
