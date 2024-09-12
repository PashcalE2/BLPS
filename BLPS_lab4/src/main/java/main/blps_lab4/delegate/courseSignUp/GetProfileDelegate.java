package main.blps_lab4.delegate.courseSignUp;

import lombok.AllArgsConstructor;
import main.blps_lab4.dto.BankCardData;
import main.blps_lab4.dto.BankCardInterface;
import main.blps_lab4.exception.ClientAlreadySignedUpException;
import main.blps_lab4.exception.UserIsBannedException;
import main.blps_lab4.exception.UserNotFoundException;
import main.blps_lab4.model.BankCard;
import main.blps_lab4.model.UserEntity;
import main.blps_lab4.repository.UserRepository;
import main.blps_lab4.repository.UsersCoursesRepository;
import main.blps_lab4.service.AuthService;
import main.blps_lab4.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetProfileDelegate implements JavaDelegate {
    private final AuthService authService;
    private final UserService userService;
    private final UsersCoursesRepository usersCoursesRepository;
    private final UserRepository userRepository;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String accessToken = (String) delegateExecution.getVariable("accessToken");
        Long courseId = (Long) delegateExecution.getVariable("courseId");

        String login = authService.getLoginFromAccessToken(accessToken);

        Optional<UserEntity> dbUser = userService.findByEmail(login);
        if (dbUser.isEmpty()) {
            throw new UserNotFoundException(login);
        }

        UserEntity user = dbUser.get();
        if (user.getBanned()) {
            throw new UserIsBannedException(user.getId());
        }

        if (usersCoursesRepository.isClientSignedUpForCourse(user.getId(), courseId)) {
            throw new ClientAlreadySignedUpException(user.getId(), courseId);
        }

        Optional<BankCardInterface> db_bankCard = userRepository.findBankCardByUserId(user.getId());
        if (db_bankCard.isEmpty()) {
            delegateExecution.setVariable("hasBankCard", false);
            return;
        }

        BankCardInterface bankCard = db_bankCard.get();

        delegateExecution.setVariable("hasBankCard", true);
        delegateExecution.setVariable("cardId", bankCard.getId());
        delegateExecution.setVariable("userId", user.getId());
    }
}
