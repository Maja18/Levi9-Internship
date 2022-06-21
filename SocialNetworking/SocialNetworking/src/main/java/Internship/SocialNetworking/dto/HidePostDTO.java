package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HidePostDTO {
    private Long postId;
    private Long personId;
}
