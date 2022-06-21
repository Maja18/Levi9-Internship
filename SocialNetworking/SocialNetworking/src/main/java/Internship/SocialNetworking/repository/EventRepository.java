package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event getByEventId(Long id);

    Event getByName(String name);
}
