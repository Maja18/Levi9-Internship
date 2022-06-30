package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class HidePostDTO {
    private Long postId; //NOSONAR
    private Long personId; //NOSONAR
}
