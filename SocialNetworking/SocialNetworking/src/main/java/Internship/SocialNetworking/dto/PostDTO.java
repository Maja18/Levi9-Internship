package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {
    private Long postId; //NOSONAR
    private String description; //NOSONAR
    private String imageUrl; //NOSONAR
    private String videoUrl; //NOSONAR
    private Boolean isPublic; //NOSONAR
    private Long creatorId; //NOSONAR
    private LocalDateTime creationDate; //NOSONAR
    private Boolean isOver; //NOSONAR
    private Long groupId; //NOSONAR
    private List<ImageDTO> images; //NOSONAR
}
