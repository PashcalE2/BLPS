package main.blps_lab2.exception;

public class CourseNotFoundException extends Exception {
    private final Long course_id;

    public CourseNotFoundException(Long course_id) {
        super("Нет такого курса");
        this.course_id = course_id;
    }

    public Long getCourseId() {
        return course_id;
    }
}
