package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.MuteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuteRequestRepository extends JpaRepository<MuteRequest, Long> {

    MuteRequest findByMuteRequestId(Long muteRequestId);

    MuteRequest findAllByPersonId(Long personId);
}
