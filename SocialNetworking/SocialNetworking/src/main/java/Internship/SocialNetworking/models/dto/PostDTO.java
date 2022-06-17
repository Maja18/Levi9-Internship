package Internship.SocialNetworking.models.dto;

import lombok.Data;

@Data
public class PostDTO {
    private String description;
    private String imageUrl;
    private String videoUrl;
    private Boolean isPublic;
    private Long groupId;
    private Long userId;

}
