package Internship.SocialNetworking.Models;

import java.util.List;


public class Group {


    private long GroupId;
    private String Name;
    private String Description;
    private boolean isPublic;
    private long CreatorId;
    private List<User> ListUsers;
}
