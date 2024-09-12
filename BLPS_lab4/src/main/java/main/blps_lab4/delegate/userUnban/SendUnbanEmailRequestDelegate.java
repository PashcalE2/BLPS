package main.blps_lab4.delegate.userUnban;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class SendUnbanEmailRequestDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution
                .getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("sendUnbanEmailRequest")
                .setVariable("userEmail", delegateExecution.getVariable("userEmail"))
                .setVariable("date", delegateExecution.getVariable("date"))
                .correlate();
    }
}