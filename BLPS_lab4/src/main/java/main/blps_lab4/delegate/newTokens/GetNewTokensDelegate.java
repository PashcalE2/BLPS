package main.blps_lab4.delegate.newTokens;

import lombok.AllArgsConstructor;
import main.blps_lab4.dto.JwtResponse;
import main.blps_lab4.dto.RefreshJwtRequest;
import main.blps_lab4.service.AuthService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetNewTokensDelegate implements JavaDelegate {
    private final AuthService authService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String refreshToken = (String) delegateExecution.getVariable("refreshToken");

        JwtResponse jwtResponse = authService.getNewRefreshToken(new RefreshJwtRequest(refreshToken));

        delegateExecution.setVariable("refreshToken", jwtResponse.getRefreshToken());
        delegateExecution.setVariable("accessToken", jwtResponse.getAccessToken());
    }
}
