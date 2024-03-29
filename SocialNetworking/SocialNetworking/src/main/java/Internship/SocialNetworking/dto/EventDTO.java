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



    @NotNull(message = "You must input date!")
    private String startEvent; //NOSONAR

    @NotNull(message = "You must input date!")
    private String endEvent; //NOSONAR

    @NotNull(message = "You must input name!")
    private String name; //NOSONAR


    private float x; //NOSONAR


    private float y; //NOSONAR


    @NotNull(message = "Group must be real!")
    private Long groupId; //NOSONAR


}
