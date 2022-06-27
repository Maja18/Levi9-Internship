package Internship.SocialNetworking.dto;

import Internship.SocialNetworking.models.FriendRequest;
import Internship.SocialNetworking.models.FriendRequestStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FriendRequestDTO {
    private Long friendRequestId;
    private FriendRequestStatus status;
}
