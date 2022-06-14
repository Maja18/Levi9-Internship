package Internship.SocialNetworking.Models;

import java.util.Date;
import java.util.List;

public class Post {
    private long PostId;
    private String Text;
    private String ImageUrl;
    private String VideoUrl;
    private boolean IsPublic;
    private long CreatorId;
    private Date CreationDate;
    private boolean IsOver;
    private List<Person> BlockList;

    public Post(){}

    public long getPostId() {
        return PostId;
    }

    public void setPostId(long postId) {
        PostId = postId;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public boolean isPublic() {
        return IsPublic;
    }

    public void setPublic(boolean aPublic) {
        IsPublic = aPublic;
    }

    public long getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(long creatorId) {
        CreatorId = creatorId;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public boolean isOver() {
        return IsOver;
    }

    public void setOver(boolean over) {
        IsOver = over;
    }

    public List<Person> getBlockList() {
        return BlockList;
    }

    public void setBlockList(List<Person> blockList) {
        BlockList = blockList;
    }
}
