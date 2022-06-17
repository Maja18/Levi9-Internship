package Internship.SocialNetworking.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class GroupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_request_id", nullable = false)
    private Long groupRequestId;

    @Column
    private RequestStatus requestStatus;

    @Column
    private Long creatorId;

    @Column
    private Long groupId;

}
