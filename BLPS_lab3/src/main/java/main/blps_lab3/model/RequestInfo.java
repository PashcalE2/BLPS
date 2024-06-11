package main.blps_lab3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RequestInfo")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class RequestInfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "request", nullable = false, length = -1)
    private String request;

    @Basic
    @Column(name = "status", nullable = false)
    private String status;

    @Basic
    @Column(name = "count", nullable = false)
    private Long count;

    @Basic
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Override
    public String toString() {
        return String.format("%d : %s : %s : %d : %s", id, request, status, count, date);
    }
}
