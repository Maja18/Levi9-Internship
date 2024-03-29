package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDTO {
    @NotBlank
    private String content; //NOSONAR
    @NotNull
    private Long postId; //NOSONAR
    private Long parentId; //NOSONAR
}
