package Internship.SocialNetworking.service;

import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.service.iService.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupNW> getAllGroups() {
        return groupRepository.findAll();
    }
}
