package Internship.SocialNetworking.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject)
    {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("isateam63@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        log.info("Message  sent...");
    }

}
