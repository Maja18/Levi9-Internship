package Internship.SocialNetworking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class EventDTO {

    private Long eventId;

    @NotNull(message = "You must input date!")
    private String startEvent;

    @NotNull(message = "You must input date!")
    private String endEvent;

    @NotNull(message = "You must input name!")
    private String name;


    private float x;


    private float y;


    private boolean isOver;


    private Long creatorId;

    @NotNull(message = "Group must be real!")
    private Long groupId;


}
