package Internship.SocialNetworking.models;


import lombok.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
//@org.springframework.data.relational.core.mapping.Table
public class GroupNW {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private boolean isPublic;

    @Column
    private Long creatorId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "group_persons", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"))
    private List<Person> members;

   /* @JsonBackReference
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)*/
    @ManyToMany
    @JoinTable(name = "group_posts", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "post_id"))
    private List<Post> posts;

}

