package main.blps_lab2.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@jakarta.persistence.IdClass(main.blps_lab2.data.UsersCoursesPK.class)
public class UsersCourses {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "user_id", nullable = false)
    private Long userId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "course_id", nullable = false)
    private Long courseId;
}
