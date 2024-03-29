package Internship.SocialNetworking.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column
    private String description;

    @Column
    private String imageUrl;

    @Column
    private String videoUrl;

    @Column
    private Boolean isPublic;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column
    private LocalDateTime creationDate;

    @Column
    private Boolean isOver;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "block_persons", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"))
    private List<Person> blockedPersons;

    /*@JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)*/
    @Column(name="group_id")
    private Long groupId;

}
