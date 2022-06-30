package Internship.SocialNetworking.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CommentException extends RuntimeException{
    public CommentException(String message){
        super(message);
    }
}
