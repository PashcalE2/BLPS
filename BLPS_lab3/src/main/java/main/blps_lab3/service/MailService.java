package main.blps_lab3.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.configuration.MailConfiguration;
import main.blps_lab3.service.interfaces.MailServiceInterface;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MailService implements MailServiceInterface {
    private final JavaMailSender emailSender;
    private final MailConfiguration mailConfiguration;

    public void sendEmail(String userEmail, String title, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);

        log.info("Отправлен email: \nкому: {} \nзаголовок: {} \nтекст: {}", userEmail, title, message);
    }

    public void sendEmailToAdmin(String title, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailConfiguration.getAdminEmail());
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);

        log.info("Отправлен email: \nкому: {} \nзаголовок: {} \nтекст: {}", mailConfiguration.getAdminEmail(), title, message);
    }
}
