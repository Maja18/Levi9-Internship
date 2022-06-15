package Internship.SocialNetworking.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Event {
   @Id
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
   public Event(){}

   public Long getEventId() {
      return eventId;
   }

   public void setEventId(Long eventId) {
      this.eventId = eventId;
   }

   public Date getStartEvent() {
      return startEvent;
   }

   public void setStartEvent(Date startEvent) {
      this.startEvent = startEvent;
   }

   public Date getEndEvent() {
      return endEvent;
   }

   public void setEndEvent(Date endEvent) {
      this.endEvent = endEvent;
   }

   public float getLocation() {
      return location;
   }

   public void setLocation(float location) {
      this.location = location;
   }

   public Long getGroupId() {
      return groupId;
   }

   public void setGroupId(Long groupId) {
      this.groupId = groupId;
   }

   public Long getCreatorId() {
      return creatorId;
   }

   public void setCreatorId(Long creatorId) {
      this.creatorId = creatorId;
   }

   public boolean isOver() {
      return isOver;
   }

   public void setOver(boolean over) {
      isOver = over;
   }

}
