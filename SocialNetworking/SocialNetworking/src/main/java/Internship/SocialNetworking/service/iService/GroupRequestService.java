package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.models.GroupRequest;
import Internship.SocialNetworking.models.dto.GroupRequestDTO;

import java.util.List;

public interface GroupRequestService {
    List<GroupRequest> listAllRequests();

    String acceptOrRejectRequest(GroupRequestDTO request,Long administratorId);
}
