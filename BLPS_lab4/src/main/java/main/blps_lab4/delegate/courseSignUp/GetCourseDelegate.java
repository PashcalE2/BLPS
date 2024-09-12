package main.blps_lab4.delegate.courseSignUp;

import lombok.AllArgsConstructor;
import main.blps_lab4.exception.CourseIsBlockedException;
import main.blps_lab4.exception.CourseNotFoundException;
import main.blps_lab4.model.Course;
import main.blps_lab4.repository.CourseRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetCourseDelegate implements JavaDelegate {
    private final CourseRepository courseRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long courseId = (Long) delegateExecution.getVariable("courseId");

        Optional<Course> db_course = courseRepository.getCourseById(courseId);
        if (db_course.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }
        Course course = db_course.get();

        if (course.getBlocked()) {
            throw new CourseIsBlockedException(courseId);
        }

        delegateExecution.setVariable("coursePrice", course.getPrice());
    }
}
