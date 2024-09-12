package main.blps_lab4.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "User")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "email", nullable = false, length = -1)
    private String email;

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;

    @Basic
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Basic
    @Column(name = "banned", nullable = false)
    private Boolean banned;
}
