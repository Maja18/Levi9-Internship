package Internship.SocialNetworking.models.dto;

import Internship.SocialNetworking.models.RequestStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@RequiredArgsConstructor
public class GroupRequestDTO {

    private Long groupRequestId;

    private RequestStatus requestStatus;

    private Long creatorId;

    private Long groupId;
}
