package main.blps_lab1.data;

import java.io.Serializable;

public class ClientsCoursesPK implements Serializable {
    private Long clientId;
    private Long courseId;

    public ClientsCoursesPK(Long clientId, Long courseId) {
        this.clientId = clientId;
        this.courseId = courseId;
    }
}
