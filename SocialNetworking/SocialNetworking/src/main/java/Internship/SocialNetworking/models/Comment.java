package Internship.SocialNetworking.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column
    private String content;

    @Column
    private Long postId;

    @Column
    private Long parentId;

    @Column
    private Long creatorId;

}
