package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.GroupRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRequestRepository extends JpaRepository<GroupRequest,Long> {

GroupRequest findByGroupRequestId(Long groupId);
}
