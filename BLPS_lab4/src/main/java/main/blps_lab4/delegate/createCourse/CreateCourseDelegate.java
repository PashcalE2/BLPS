package main.blps_lab4.delegate.createCourse;

import lombok.AllArgsConstructor;
import main.blps_lab4.model.Course;
import main.blps_lab4.service.AdminService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateCourseDelegate implements JavaDelegate {
    private final AdminService adminService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String courseName = (String) delegateExecution.getVariable("courseName");
        Integer price = (Integer) delegateExecution.getVariable("price");

        adminService.createNewCourse(new Course(null, courseName, price, false));
    }
}
