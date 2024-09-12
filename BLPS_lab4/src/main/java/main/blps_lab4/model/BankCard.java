package main.blps_lab4.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BankCard")
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class BankCard {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Basic
    @Column(name = "serial_number", nullable = false, length = -1)
    private String serialNumber;

    @Basic
    @Column(name = "validity_date", nullable = false, length = -1)
    private String validityDate;

    @Basic
    @Column(name = "cvv", nullable = false, length = -1)
    private String cvv;

    @Basic
    @Column(name = "money", nullable = false)
    private Integer money;
}
