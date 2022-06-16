package Internship.SocialNetworking.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
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
