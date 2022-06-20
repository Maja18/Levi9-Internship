package Internship.SocialNetworking.repository;

import Internship.SocialNetworking.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentId(Long parentId);

    List<Comment> findByPostIdOrderByCreationDateDesc(Long postId);
}
