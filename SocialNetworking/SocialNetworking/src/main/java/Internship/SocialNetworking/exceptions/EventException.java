package Internship.SocialNetworking.exceptions;

import Internship.SocialNetworking.models.Event;
import Internship.SocialNetworking.models.GroupNW;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EventException extends RuntimeException {


    public EventException(String message){
        super(message);
    }



}
