package main.blps_lab2.exception;

public class ClientAlreadySignedUpException extends Exception {
    private final Long client_id;
    private final Long course_id;

    public ClientAlreadySignedUpException(Long client_id, Long course_id) {
        super("Вы уже записаны на этот курс");
        this.client_id = client_id;
        this.course_id = course_id;
    }

    public Long getClientId() {
        return client_id;
    }

    public Long getCourseId() {
        return course_id;
    }
}
