package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.GroupNW;
import Internship.SocialNetworking.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
