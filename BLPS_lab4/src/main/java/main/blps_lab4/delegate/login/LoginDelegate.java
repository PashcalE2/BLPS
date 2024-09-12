package main.blps_lab4.delegate.login;

import lombok.AllArgsConstructor;
import main.blps_lab4.dto.JwtRequest;
import main.blps_lab4.dto.JwtResponse;
import main.blps_lab4.service.AuthService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginDelegate implements JavaDelegate {
    private final AuthService authService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String login = (String) delegateExecution.getVariable("email");
        String password = (String) delegateExecution.getVariable("password");

        JwtResponse jwtResponse = authService.login(new JwtRequest(login, password));

        delegateExecution.setVariable("refreshToken", jwtResponse.getRefreshToken());
        delegateExecution.setVariable("accessToken", jwtResponse.getAccessToken());
    }
}
