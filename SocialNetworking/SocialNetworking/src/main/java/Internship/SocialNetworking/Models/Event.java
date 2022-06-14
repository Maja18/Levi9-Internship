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
private List<User> ListUsersAccepted;
}
