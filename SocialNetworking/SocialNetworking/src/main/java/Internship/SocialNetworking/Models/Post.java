package Internship.SocialNetworking.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long PostId;
    private String Text;
    private String ImageUrl;
    private String VideoUrl;
    public boolean IsPublic;
    public long CreatorId;
    private Date CreationDate;
    public boolean IsOver;
    //public List<Person> BlockList;
}
