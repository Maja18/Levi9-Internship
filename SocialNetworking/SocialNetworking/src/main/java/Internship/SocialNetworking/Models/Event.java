package Internship.SocialNetworking.Models;

import java.util.Date;
import java.util.List;


public class Event {
   private long EventId;
   private Date StartEvent;
   private Date EndEvent;
   private float Location;
   private long GroupId;
   private long CreatorId;
   private boolean IsOver;
   private List<Person> ListUsersAccepted;

   public Event(){}

   public long getEventId() {
      return EventId;
   }

   public void setEventId(long eventId) {
      EventId = eventId;
   }

   public Date getStartEvent() {
      return StartEvent;
   }

   public void setStartEvent(Date startEvent) {
      StartEvent = startEvent;
   }

   public Date getEndEvent() {
      return EndEvent;
   }

   public void setEndEvent(Date endEvent) {
      EndEvent = endEvent;
   }

   public float getLocation() {
      return Location;
   }

   public void setLocation(float location) {
      Location = location;
   }

   public long getGroupId() {
      return GroupId;
   }

   public void setGroupId(long groupId) {
      GroupId = groupId;
   }

   public long getCreatorId() {
      return CreatorId;
   }

   public void setCreatorId(long creatorId) {
      CreatorId = creatorId;
   }

   public boolean isOver() {
      return IsOver;
   }

   public void setOver(boolean over) {
      IsOver = over;
   }

   public List<Person> getListUsersAccepted() {
      return ListUsersAccepted;
   }

   public void setListUsersAccepted(List<Person> listUsersAccepted) {
      ListUsersAccepted = listUsersAccepted;
   }
}
