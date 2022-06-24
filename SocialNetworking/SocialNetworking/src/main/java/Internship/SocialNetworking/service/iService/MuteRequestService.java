package Internship.SocialNetworking.service.iService;

import Internship.SocialNetworking.dto.MuteRequestDTO;
import Internship.SocialNetworking.models.MuteRequest;

import java.util.List;

public interface MuteRequestService {
    List<MuteRequest> getAllMuteRequests();
    void saveMuteRequest(MuteRequest muteRequest);

    String muteGroup(MuteRequestDTO muteRequestDTO);

    boolean isGroupBlockedPermanently(Long personId, Long groupId);
}
