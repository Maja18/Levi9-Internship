package Internship.SocialNetworking.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_request_id", nullable = false)
    private Long friendRequestId;

    @Column(name = "friend_id")
    private Long friendId;

    @Column
    private FriendRequestStatus status;
}
