package main.blps_lab4.exception;

import lombok.Getter;

@Getter
public class CourseNotFoundException extends BaseException {
    private final Long courseId;

    public CourseNotFoundException(Long courseId) {
        super("Нет такого курса");
        this.courseId = courseId;
    }
}
