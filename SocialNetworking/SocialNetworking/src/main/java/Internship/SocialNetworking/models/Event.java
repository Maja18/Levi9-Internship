package Internship.SocialNetworking.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Event {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "event_id", nullable = false)
   private Long eventId;

   @Column
   private LocalDateTime startEvent;

   @Column
   private LocalDateTime endEvent;

   @Column
   private String name;

  @Column
  private float x;

  @Column
  private float y;

   @Column
   private Long groupId;

   @Column
   private Long creatorId;

   @Column
   private boolean isOver;

}
