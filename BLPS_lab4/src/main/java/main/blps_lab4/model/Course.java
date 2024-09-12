package main.blps_lab4.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Course")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    private String name;

    @Basic
    @Column(name = "price", nullable = false)
    private Integer price;

    @Basic
    @Column(name = "blocked", nullable = false)
    private Boolean blocked;
}
