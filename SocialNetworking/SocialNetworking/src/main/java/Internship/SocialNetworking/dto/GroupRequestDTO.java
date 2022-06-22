package Internship.SocialNetworking.dto;

import Internship.SocialNetworking.models.RequestStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class GroupRequestDTO {

    private Long groupRequestId;

    private RequestStatus requestStatus;

    private Long creatorId;

    private Long groupId;
}
