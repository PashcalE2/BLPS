package main.blps_lab3.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.service.interfaces.MailServiceInterface;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Profile("subscriber")
@Service
@Slf4j
@AllArgsConstructor
public class MailService implements MailServiceInterface {
    private final JavaMailSender emailSender;

    public void sendEmail(String userEmail, String title, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);

        log.info("Отправлен email: \nкому: {} \nзаголовок: {} \nтекст: {}", userEmail, title, message);
    }
}
