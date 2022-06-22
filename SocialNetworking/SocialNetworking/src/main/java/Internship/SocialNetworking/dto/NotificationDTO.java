package Internship.SocialNetworking.dto;

import Internship.SocialNetworking.models.PostMuteStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@RequiredArgsConstructor
public class NotificationDTO {


    private Long notificationId;

    @NotBlank(message = "content is required")
    private String content;

    @NotBlank(message = "source is required")
    private String source;

    @NotBlank(message = "sender is required")
    private String sender;

    @NotBlank(message = "receiver is required")
    private String receiver;
    @NotBlank(message = "mute status is required")
    private PostMuteStatus postMuteStatus;
}
