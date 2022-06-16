package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.GroupNW;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupNW, Long> {


    public GroupNW findByGroupIdEquals(Long groupId);
    public GroupNW findByNameEquals(String name);


}
