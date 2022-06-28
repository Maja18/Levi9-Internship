package Internship.SocialNetworking.service;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendSimpleEmail(String toEimail,
                                String body,
                                String subject)
    {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("isateam63@gmail.com");
        message.setTo(toEimail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Message  sent...");
    }

}
