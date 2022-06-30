package Internship.SocialNetworking.service.interface_service;

import Internship.SocialNetworking.models.GroupRequest;

import java.util.List;

public interface GroupRequestService {
    List<GroupRequest> listAllRequests(Long personId);

    String acceptOrRejectRequest(Long requestId,Long administratorId,Long approvalStatus);
}
