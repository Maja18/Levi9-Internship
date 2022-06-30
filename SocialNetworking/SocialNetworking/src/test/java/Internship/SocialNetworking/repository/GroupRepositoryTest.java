package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.GroupNW;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class GroupRepositoryTest {

    @Resource
    private GroupRepository groupRepository;

    @Test
    void checkIfGroupExistsById(){
        assertThat(groupRepository.findByGroupId(1L)).isNotNull();

    }

    @Test
    void doesGroupExistsByName(){
        GroupNW groupNW = groupRepository.findByNameEquals("Group 1");
        assertThat(groupNW).isNotNull();

    }

    @Test
    void getAllGroups(){
        assertThat(groupRepository.findAll()).isNotEmpty();
    }


}