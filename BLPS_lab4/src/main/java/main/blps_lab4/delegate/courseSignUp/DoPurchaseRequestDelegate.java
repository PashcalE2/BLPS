package main.blps_lab4.delegate.courseSignUp;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class DoPurchaseRequestDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution
                .getProcessEngineServices()
                .getRuntimeService()
                .createMessageCorrelation("doPurchaseRequest")
                .setVariable("cardId", delegateExecution.getVariable("cardId"))
                .setVariable("coursePrice", delegateExecution.getVariable("coursePrice"))
                .correlate();
    }
}
