package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByIdEquals(Long id);
    Authority findByNameEquals(String name);
}
