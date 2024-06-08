package main.blps_lab3.exception;

import lombok.Getter;

@Getter
public class CourseIsBlockedException extends Exception {
    private final Long courseId;

    public CourseIsBlockedException(Long courseId) {
        super("Этот курс заблокирован");
        this.courseId = courseId;
    }
}
