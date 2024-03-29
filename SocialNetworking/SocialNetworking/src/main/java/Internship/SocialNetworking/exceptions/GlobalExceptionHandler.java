package Internship.SocialNetworking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PersonException.class, GroupException.class, MediaException.class,
                        FriendRequestException.class, CommentException.class, PostException.class})
    public ResponseEntity<Object> entityNotFoundException(RuntimeException runtimeException, WebRequest webRequest){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", runtimeException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
