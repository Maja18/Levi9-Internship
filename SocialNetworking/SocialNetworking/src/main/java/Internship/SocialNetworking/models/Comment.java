package Internship.SocialNetworking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


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

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "comments",
            joinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id", referencedColumnName = "comment_id"))
    private List<Comment> comments;

}
