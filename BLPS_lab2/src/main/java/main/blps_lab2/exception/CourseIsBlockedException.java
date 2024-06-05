package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class CourseIsBlockedException extends Exception {
    private final Long courseId;

    public CourseIsBlockedException(Long courseId) {
        super("Этот курс заблокирован");
        this.courseId = courseId;
    }
}
