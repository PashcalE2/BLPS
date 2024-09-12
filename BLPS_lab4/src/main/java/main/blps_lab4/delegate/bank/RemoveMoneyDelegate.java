package main.blps_lab4.delegate.bank;

import lombok.AllArgsConstructor;
import main.blps_lab4.service.BankService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemoveMoneyDelegate implements JavaDelegate {
    private final BankService bankService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long cardId = (Long) delegateExecution.getVariable("cardId");
        Integer coursePrice = (Integer) delegateExecution.getVariable("coursePrice");

        bankService.removeMoney(cardId, coursePrice);
    }
}
