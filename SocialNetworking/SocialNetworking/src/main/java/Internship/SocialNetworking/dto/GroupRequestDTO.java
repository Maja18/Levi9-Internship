package Internship.SocialNetworking.dto;

import Internship.SocialNetworking.models.RequestStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class GroupRequestDTO {

    private Long groupRequestId; //NOSONAR

    private RequestStatus requestStatus; //NOSONAR

    private Long creatorId; //NOSONAR

    private Long groupId; //NOSONAR
}
