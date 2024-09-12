package main.blps_lab4.delegate.sendBanEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.service.interfaces.MailServiceInterface;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
@Slf4j
public class SendBanEmailDelegate implements JavaDelegate {
    private final MailServiceInterface mailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String userEmail = (String) delegateExecution.getVariable("userEmail");
        Date date = (Date) delegateExecution.getVariable("date");

        log.info("SendEmail: attempt to send an email");
        mailService.sendEmail(
                userEmail,
                "Вам бан от " + date,
                "Тестируем org.springframework.mail.javamail.JavaMailSender"
        );
    }
}
