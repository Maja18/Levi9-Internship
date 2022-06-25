package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.GroupRequest;
import Internship.SocialNetworking.dto.GroupRequestDTO;

import java.util.List;

public interface GroupRequestService {
    List<GroupRequest> listAllRequests();

    String acceptOrRejectRequest(Long requestId,Long administratorId,Long approvalStatus);
}
