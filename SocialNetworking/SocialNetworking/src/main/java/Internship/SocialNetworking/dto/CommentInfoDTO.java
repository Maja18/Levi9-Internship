package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentInfoDTO {
    private String content; //NOSONAR
    private LocalDateTime creationDate; //NOSONAR
}
