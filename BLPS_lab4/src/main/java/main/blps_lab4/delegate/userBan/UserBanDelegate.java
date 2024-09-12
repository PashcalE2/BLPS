package main.blps_lab4.delegate.userBan;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.model.UserEntity;
import main.blps_lab4.service.AdminService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@AllArgsConstructor
@Slf4j
public class UserBanDelegate implements JavaDelegate {
    private final AdminService adminService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long userId = (Long) delegateExecution.getVariable("userId");

        log.info("UserBan: attempt to ban user");
        UserEntity user = adminService.banUser(userId);

        Date now = Date.from(Instant.now());
        delegateExecution.setVariable("userEmail", user.getEmail());
        delegateExecution.setVariable("date", now);
    }
}
