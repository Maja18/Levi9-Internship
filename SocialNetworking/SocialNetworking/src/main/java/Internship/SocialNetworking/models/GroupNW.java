package Internship.SocialNetworking.models;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class GroupNW {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
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
    @JoinTable(name = "person_groups_members", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "person_id"))
    private List<Person> members;

}

