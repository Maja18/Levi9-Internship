package Internship.SocialNetworking.service.interface_service;

import Internship.SocialNetworking.dto.MuteRequestDTO;
import Internship.SocialNetworking.models.MuteRequest;

import java.util.List;

public interface MuteRequestService {
    List<MuteRequest> getAllMuteRequests();
    void saveMuteRequest(MuteRequest muteRequest);

    MuteRequestDTO muteGroup(MuteRequestDTO muteRequestDTO, Long userId);

    boolean isGroupBlockedPermanently(Long personId, Long groupId);

    boolean isGroupBlockedTemporary(Long personId, Long groupId);

}
