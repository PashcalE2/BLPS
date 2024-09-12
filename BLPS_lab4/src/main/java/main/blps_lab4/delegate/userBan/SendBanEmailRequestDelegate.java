package main.blps_lab4.delegate.userBan;

import lombok.AllArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendBanEmailRequestDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution
                .getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("sendBanEmailRequest")
                .setVariable("userEmail", delegateExecution.getVariable("userEmail"))
                .setVariable("date", delegateExecution.getVariable("date"))
                .correlate();
    }
}
