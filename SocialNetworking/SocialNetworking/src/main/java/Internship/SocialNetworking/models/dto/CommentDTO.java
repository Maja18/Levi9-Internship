package Internship.SocialNetworking.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDTO {
    @NotBlank
    private String content;
    @NotNull
    private Long postId;
    private Long parentId;
    @NotNull
    private Long creatorId;
}
