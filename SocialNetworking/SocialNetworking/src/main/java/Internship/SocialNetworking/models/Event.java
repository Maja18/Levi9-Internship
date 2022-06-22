package Internship.SocialNetworking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
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

   @Column
   private Boolean notified;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "event_persons",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"))
    private List<Person> going;

   /*public boolean getIsNotified(){
       return this.isNotified;
   } */

  // public void setIsNotified(boolean isNotified){
       //this.isNotified = isNotified;
   //}


}
