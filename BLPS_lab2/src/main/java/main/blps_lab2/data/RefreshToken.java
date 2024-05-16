package main.blps_lab2.data;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@EqualsAndHashCode
@Getter
@Setter
public class RefreshToken {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Basic
    @Column(name = "token", nullable = false, length = -1)
    private String token;

    @Basic
    @Column(name = "expires_at", nullable = false)
    private Timestamp expiresAt;
}
