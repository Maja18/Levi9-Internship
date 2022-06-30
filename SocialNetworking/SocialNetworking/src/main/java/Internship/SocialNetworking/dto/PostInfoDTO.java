package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class PostInfoDTO {
    private String description; //NOSONAR
    private String imageUrl; //NOSONAR
    private String videoUrl; //NOSONAR
    private boolean isPublic; //NOSONAR
    private LocalDateTime creationDate; //NOSONAR
    private boolean isOver; //NOSONAR
}
