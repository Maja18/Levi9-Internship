package Internship.SocialNetworking.exceptions;

import Internship.SocialNetworking.models.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PersonException extends RuntimeException{

    private Person person;
    private Long personId;

    public PersonException(String message){
        super(message);
    }

    public PersonException(Person person, String message){
        super(message);
        this.person = person;
    }

    public PersonException(Long personId, String message){
        super(message);
        this.personId= personId;
    }
}
