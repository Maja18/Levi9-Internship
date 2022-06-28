package Internship.SocialNetworking.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ExceptionHandler extends RuntimeException{

        public ExceptionHandler(String errorMessage){
                super(errorMessage);
        }

        }
