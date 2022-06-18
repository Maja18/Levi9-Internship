package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthorityId(Long id);
    Authority findByNameEquals(String name);
}
