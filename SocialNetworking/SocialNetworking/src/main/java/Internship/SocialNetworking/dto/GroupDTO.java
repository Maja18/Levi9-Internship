package Internship.SocialNetworking.dto;

import Internship.SocialNetworking.models.Person;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class GroupDTO {




    @NotNull(message = "Group must have a name!")
    @NotBlank(message = "Group must have a name!")
    private String name;

    private String description;

    private boolean isPublic;





    public boolean getIsPublic(){
        return this.isPublic;
    }

}
