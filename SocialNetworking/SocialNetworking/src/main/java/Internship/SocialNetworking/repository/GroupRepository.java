package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Authority;
import Internship.SocialNetworking.models.GroupNW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupNW, Long> {
    List<GroupNW> findAll();

}
