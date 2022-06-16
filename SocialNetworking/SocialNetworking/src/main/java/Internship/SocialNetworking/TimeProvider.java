package Internship.SocialNetworking;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class TimeProvider {
    private static final long serialVersionUID = 1L;


    public Date now() {
        return new Date();
    }

    public Timestamp nowTimestamp(){
        return new Timestamp(new Date().getTime());
    }
}
