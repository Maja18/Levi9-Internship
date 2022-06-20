package Internship.SocialNetworking.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)

    private Long commentId;

    @Column
    private String content;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

}
