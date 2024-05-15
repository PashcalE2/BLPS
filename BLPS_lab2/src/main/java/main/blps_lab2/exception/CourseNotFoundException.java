package main.blps_lab2.exception;

import lombok.Getter;

@Getter
public class CourseNotFoundException extends Exception {
    private final Long courseId;

    public CourseNotFoundException(Long courseId) {
        super("Нет такого курса");
        this.courseId = courseId;
    }
}
