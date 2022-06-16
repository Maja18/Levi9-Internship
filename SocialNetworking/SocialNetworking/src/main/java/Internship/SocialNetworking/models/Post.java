package Internship.SocialNetworking.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Post {

    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column
    private String content;

    @Column
    private String imageUrl;

    @Column
    private String videoUrl;

    @Column
    private boolean isPublic;

    @Column
    private Long creatorId;

    @Column
    private Date creationDate;

    @Column
    private boolean isOver;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "blocked_posts_blocked_persons", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"))
    private List<Person> blockedPersons;
}
