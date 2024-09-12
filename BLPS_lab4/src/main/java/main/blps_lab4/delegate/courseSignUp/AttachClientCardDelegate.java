package main.blps_lab4.delegate.courseSignUp;

import lombok.AllArgsConstructor;
import main.blps_lab4.dto.BankCardCredentials;
import main.blps_lab4.service.ClientService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AttachClientCardDelegate implements JavaDelegate {
    private final ClientService clientService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accessToken = (String) delegateExecution.getVariable("accessToken");
        String serialNumber = (String) delegateExecution.getVariable("serialNumber");
        String validityDate = (String) delegateExecution.getVariable("validityDate");
        String cvv = (String) delegateExecution.getVariable("cvv");

        clientService.attachClientCard(accessToken, new BankCardCredentials(serialNumber, validityDate, cvv));
    }
}
