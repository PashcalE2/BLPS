package main.blps_lab4.delegate.logout;

import lombok.AllArgsConstructor;
import main.blps_lab4.service.AuthService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogoutDelegate implements JavaDelegate {
    private final AuthService authService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accessToken = (String) delegateExecution.getVariable("accessToken");

        authService.logout(accessToken);
    }
}
