package main.blps_lab2.data;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "UsersCourses")
@EqualsAndHashCode
@Getter
@Setter
@IdClass(main.blps_lab2.data.UsersCoursesPK.class)
public class UsersCourses {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "course_id", nullable = false)
    private Long courseId;
}
