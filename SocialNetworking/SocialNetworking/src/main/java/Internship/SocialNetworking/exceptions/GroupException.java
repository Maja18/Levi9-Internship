package Internship.SocialNetworking.exceptions;

import Internship.SocialNetworking.models.GroupNW;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GroupException extends RuntimeException {

    private GroupNW group;
    private Long groupId;

    public GroupException(String message){
        super(message);
    }

    public GroupException(GroupNW group, String message){
        super(message);
        this.group = group;
    }

    public GroupException(Long groupId, String message){
        super(message);
        this.groupId= groupId;
    }
}
