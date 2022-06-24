package Internship.SocialNetworking.models;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class MuteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mute_request_id", nullable = false)
    private Long muteRequestId;

    @Column
    private Long groupId;

    @Column
    private Long personId;

    @Column
    private LocalDateTime muteStart;

    @Column
    private String muteEnd;






}
