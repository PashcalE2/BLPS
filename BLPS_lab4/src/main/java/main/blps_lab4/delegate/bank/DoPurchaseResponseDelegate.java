package main.blps_lab4.delegate.bank;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class DoPurchaseResponseDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution
                .getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("doPurchaseResponse")
                .setVariable("success",  delegateExecution.getVariable("success"))
                .correlate();
    }
}
