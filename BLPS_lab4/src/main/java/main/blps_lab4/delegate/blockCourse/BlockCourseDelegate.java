package main.blps_lab4.delegate.blockCourse;

import lombok.AllArgsConstructor;
import main.blps_lab4.service.AdminService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BlockCourseDelegate implements JavaDelegate {
    private final AdminService adminService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long courseId = (Long) delegateExecution.getVariable("courseId");
        adminService.blockCourse(courseId);
    }
}