package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {
    private Long postId;
    private String description;
    private String imageUrl;
    private String videoUrl;
    private Boolean isPublic;
    private Long creatorId;
    private LocalDateTime creationDate;
    private Boolean isOver;
    private Long groupId;
}
