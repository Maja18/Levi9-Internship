package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    FriendRequest findByFriendRequestId(Long friendRequestId);
}
