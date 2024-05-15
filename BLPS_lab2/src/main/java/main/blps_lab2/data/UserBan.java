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
public class UserBan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @jakarta.persistence.Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Basic
    @Column(name = "banned_on", nullable = false)
    private Timestamp bannedOn;

    @Basic
    @Column(name = "unban_on", nullable = true)
    private Timestamp unbanOn;

    @Basic
    @Column(name = "comment", nullable = true, length = -1)
    private String comment;
}
