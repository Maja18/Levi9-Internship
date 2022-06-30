package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.GroupNW;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class GroupRepositoryTest {

    @Resource
    private GroupRepository groupRepository;

    @Test
    void checkIfGroupExistsById(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);
        groupRepository.save(groupNW);
        GroupNW g1 = groupRepository.findByGroupId(1L);
        assertThat(g1.getGroupId()).isEqualTo(1L);

    }

    @Test
    void doesGroupExistsByName(){
        GroupNW groupNW = new GroupNW();
        groupNW.setGroupId(1L);
        groupNW.setName("G1");
        groupRepository.save(groupNW);
        GroupNW g1 = groupRepository.findByNameEquals("G1");
        assertThat(g1.getName()).isEqualTo("G1");

    }

    @Test
    void getAllGroups(){
        GroupNW g1 = new GroupNW();
        g1.setGroupId(1L);
        GroupNW g2 = new GroupNW();
        g2.setGroupId(2L);
        GroupNW g3 = new GroupNW();
        g3.setGroupId(3L);
        groupRepository.save(g1);
        groupRepository.save(g2);
        groupRepository.save(g3);
        List<GroupNW> list = groupRepository.findAll();

        assertThat(list).isNotEmpty();
    }


}