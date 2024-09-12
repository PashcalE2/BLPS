package main.blps_lab4.delegate.updateCourse;

import lombok.AllArgsConstructor;
import main.blps_lab4.model.Course;
import main.blps_lab4.service.AdminService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateCourseDelegate implements JavaDelegate {
    private final AdminService adminService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long courseId = (Long) delegateExecution.getVariable("courseId");
        String courseName = (String) delegateExecution.getVariable("courseName");
        Integer price = (Integer) delegateExecution.getVariable("price");

        adminService.updateCourseById(new Course(courseId, courseName, price, null));
    }
}
