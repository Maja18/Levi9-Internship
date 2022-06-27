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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupRequestServiceImpl implements GroupRequestService {

    private final GroupRequestRepository groupRequestRepository;
    private final PersonRepository personRepository;
    private  final GroupRepository groupRepository;

    @Override
    public List<GroupRequest> listAllRequests(Long personId)
    {
       List<GroupRequest> groupRequestsList=groupRequestRepository.findAll();
       List<GroupRequest> administratorList=new ArrayList<>();
       for(GroupRequest adminRequests : groupRequestsList) {
           GroupNW group=groupRepository.findByGroupId(adminRequests.getGroupId());
           if(Objects.equals(group.getCreatorId(), personId)) {
               administratorList.add(adminRequests);
           }
       }
       return administratorList;
    }

    @Override
    public String acceptOrRejectRequest(Long requestId,Long administratorId,Long approvalStatus) {
        GroupRequest groupRequest=groupRequestRepository.findByGroupRequestId(requestId);
        if(groupRequest!= null) {
            if (approvalStatus == 0)
                groupRequest.setRequestStatus(RequestStatus.ACCEPTED);
            if (groupRequest.getRequestStatus() == RequestStatus.ACCEPTED) {
                GroupNW group = groupRepository.findByGroupId(groupRequest.getGroupId());
                Person acceptedPerson = personRepository.findByPersonId(groupRequest.getCreatorId());
                if (group != null) {
                    //checking whether request sender is authorized namely is administrator of a group
                    if (Objects.equals(administratorId, group.getCreatorId())) {
                        group.getMembers().add(acceptedPerson);
                        personRepository.save(acceptedPerson);
                        //we change the value of request from pending to accepted
                        groupRequestRepository.save(groupRequest);
                        return "Successfully updated!";
                    } else {
                        return "No permission";
                    }
                }
                return null;
            }
            return "Rejected";
        }
        return "No request";
    }



}
