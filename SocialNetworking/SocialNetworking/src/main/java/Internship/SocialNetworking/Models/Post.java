package Internship.SocialNetworking.Models;

import java.util.Date;
import java.util.List;

public class Post {

    private long PostId;
    private String Text;
    private String ImageUrl;
    private String VideoUrl;
    public boolean IsPublic;
    public long CreatorId;
    private Date CreationDate;
    public boolean IsOver;
    public List<User> BlockList;
}
