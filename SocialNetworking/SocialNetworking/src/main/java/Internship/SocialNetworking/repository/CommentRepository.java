package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment getByCommentId(Long parentId);
}
