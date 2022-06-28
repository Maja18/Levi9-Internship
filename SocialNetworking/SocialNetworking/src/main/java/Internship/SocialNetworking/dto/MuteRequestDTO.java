package Internship.SocialNetworking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class MuteRequestDTO {



    private Long groupId;

    private LocalDateTime muteStart;

    private String muteEnd;


}
