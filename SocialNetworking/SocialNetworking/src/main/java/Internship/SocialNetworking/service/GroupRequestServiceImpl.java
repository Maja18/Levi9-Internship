package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.GroupRequest;
import Internship.SocialNetworking.models.Person;
import Internship.SocialNetworking.models.RequestStatus;
import Internship.SocialNetworking.dto.GroupRequestDTO;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.repository.GroupRequestRepository;
import Internship.SocialNetworking.repository.PersonRepository;
import Internship.SocialNetworking.service.iService.GroupRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupRequestServiceImpl implements GroupRequestService {

    private final GroupRequestRepository groupRequestRepository;
    private final PersonRepository personRepository;
    private  final GroupRepository groupRepository;

    @Override
    public List<GroupRequest> listAllRequests() {
       return groupRequestRepository.findAll();
    }

    @Override
    public String acceptOrRejectRequest(GroupRequestDTO request,Long administratorId) {
        GroupRequest groupRequest=groupRequestRepository.findByGroupRequestId(request.getGroupRequestId());
        groupRequest.setGroupRequestId(request.getGroupRequestId());
        groupRequest.setRequestStatus(request.getRequestStatus());
        groupRequest.setGroupId(request.getGroupId());
        groupRequest.setCreatorId(request.getCreatorId());
        if(groupRequest.getRequestStatus() == RequestStatus.ACCEPTED) {
            GroupNW group =groupRepository.findByGroupId(groupRequest.getGroupId());
            Person acceptedPerson=personRepository.findByPersonId(groupRequest.getCreatorId());
            if(group!=null) {
                //checking whether request sender is authorized namely is administrator of a group
                if(administratorId == group.getCreatorId()) {
                    group.getMembers().add(acceptedPerson);
                    personRepository.save(acceptedPerson);
                    //we change the value of request from pending to accepted
                    groupRequestRepository.save(groupRequest);
                    return "Successfully updated!";
                }
                else {
                    return "No permission";
                }
            }
            return null;
        }
        return null;
    }



}
