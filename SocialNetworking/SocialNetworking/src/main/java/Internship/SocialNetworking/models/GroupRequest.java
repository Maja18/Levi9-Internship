package Internship.SocialNetworking.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
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
