package Internship.SocialNetworking.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MediaException extends RuntimeException{

    public MediaException(String message){
        super(message);
    }
}
