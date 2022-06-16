package Internship.SocialNetworking.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class GroupRequest {
    @Id
    @SequenceGenerator(name = "mySeqGenV2", sequenceName = "mySeqV2", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV2")
    @Column(name = "group_request_id", nullable = false)
    private Long groupRequestId;

    @Column
    private RequestStatus requestStatus;

    @Column
    private Long creatorId;

    @Column
    private Long groupId;

}
