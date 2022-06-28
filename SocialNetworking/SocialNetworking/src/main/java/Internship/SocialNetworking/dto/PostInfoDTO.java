package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class PostInfoDTO {
    private String description;
    private String imageUrl;
    private String videoUrl;
    private boolean isPublic;
    private LocalDateTime creationDate;
    private boolean isOver;
}
