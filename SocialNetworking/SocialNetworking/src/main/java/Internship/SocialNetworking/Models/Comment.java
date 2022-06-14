package Internship.SocialNetworking.Models;

public class Comment {
    private long CommentId;
    private String Content;
    private long PostId;
    private long ParentId;
    private long CreatorId;

    public Comment(){}

    public long getCommentId() {
        return CommentId;
    }

    public void setCommentId(long commentId) {
        CommentId = commentId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public long getPostId() {
        return PostId;
    }

    public void setPostId(long postId) {
        PostId = postId;
    }

    public long getParentId() {
        return ParentId;
    }

    public void setParentId(long parentId) {
        ParentId = parentId;
    }

    public long getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(long creatorId) {
        CreatorId = creatorId;
    }
}
