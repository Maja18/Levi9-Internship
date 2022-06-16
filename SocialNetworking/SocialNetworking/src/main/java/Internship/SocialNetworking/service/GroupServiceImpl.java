package Internship.SocialNetworking.service;

import Internship.SocialNetworking.dto.GroupDTO;
import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.repository.GroupRepository;
import Internship.SocialNetworking.service.iService.GroupService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    public GroupNW getGroupById(Long id) {
        return groupRepository.findByGroupIdEquals(id);
    }

    @Override
    public void save(GroupNW groupNW) {
        groupRepository.save(groupNW);
    }

    @Override
    public String createGroup(GroupDTO groupDTO) {
        if(getByName(groupDTO.getName()) != null){
            return "Group name already exists, make a new one!";
        }
        else {
            GroupNW groupNW = new GroupNW();
            groupNW.setCreatorId(groupDTO.getCreatorId());
            groupNW.setDescription(groupDTO.getDescription());
            //groupNW.setPublic(groupDTO.isPublic());
            groupNW.setName(groupDTO.getName());
            save(groupNW);
            return "Group successfully made!";
        }


    }

    @Override
    public GroupNW getByName(String name) {
        return groupRepository.findByNameEquals(name);
    }
}
