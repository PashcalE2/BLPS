package main.blps_lab4.delegate.courseSignUp;

import lombok.AllArgsConstructor;
import main.blps_lab4.dto.BankCardCredentials;
import main.blps_lab4.dto.BankCardInterface;
import main.blps_lab4.exception.ClientCardDataIsMissingException;
import main.blps_lab4.repository.UserRepository;
import main.blps_lab4.service.ClientService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AttachClientCardDelegate implements JavaDelegate {
    private final ClientService clientService;
    private final UserRepository userRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accessToken = (String) delegateExecution.getVariable("accessToken");
        String serialNumber = (String) delegateExecution.getVariable("serialNumber");
        String validityDate = (String) delegateExecution.getVariable("validityDate");
        String cvv = (String) delegateExecution.getVariable("cvv");
        Long userId = (Long) delegateExecution.getVariable("userId");

        clientService.attachClientCard(accessToken, new BankCardCredentials(serialNumber, validityDate, cvv));

        Optional<BankCardInterface> db_bankCard = userRepository.findBankCardByUserId(userId);
        if (db_bankCard.isEmpty()) {
            delegateExecution.setVariable("hasBankCard", false);
            throw new ClientCardDataIsMissingException(userId);
        }

        BankCardInterface bankCard = db_bankCard.get();

        delegateExecution.setVariable("hasBankCard", true);
        delegateExecution.setVariable("cardId", bankCard.getId());
    }
}
