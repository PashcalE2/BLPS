package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class CourseIsBlockedException extends BaseException {
    private final Long courseId;

    public CourseIsBlockedException(Long courseId) {
        super("Этот курс заблокирован");
        this.courseId = courseId;
    }
}
