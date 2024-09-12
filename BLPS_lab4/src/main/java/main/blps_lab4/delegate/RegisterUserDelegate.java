package main.blps_lab4.delegate;

import lombok.AllArgsConstructor;
import main.blps_lab4.model.RoleEnum;
import main.blps_lab4.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegisterUserDelegate implements JavaDelegate {
    private final UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String email = (String) delegateExecution.getVariable("email");
        String password = (String) delegateExecution.getVariable("password");

        userService.registerUser(email, password, RoleEnum.CLIENT);
    }
}
