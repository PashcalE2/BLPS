package main.blps_lab4.delegate.courseSignUp;

import lombok.AllArgsConstructor;
import main.blps_lab4.repository.UsersCoursesRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseSignUpDelegate implements JavaDelegate {
    private final UsersCoursesRepository usersCoursesRepository;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long userId = (Long) delegateExecution.getVariable("userId");
        Long courseId = (Long) delegateExecution.getVariable("courseId");

        usersCoursesRepository.courseSignUp(userId, courseId);
    }
}
