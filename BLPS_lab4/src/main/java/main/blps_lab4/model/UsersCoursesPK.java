package main.blps_lab4.model;

import jakarta.persistence.Id;

import java.io.Serializable;

public class UsersCoursesPK implements Serializable {
    @Id
    private Long userId;
    @Id
    private Long courseId;
}
