package Internship.SocialNetworking.models.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {
    private String description;
    private String imageUrl;
    private String videoUrl;
    private Boolean isPublic;
    private Long groupId;
    private Long userId;

}
