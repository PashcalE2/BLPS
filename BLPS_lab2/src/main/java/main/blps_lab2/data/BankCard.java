package main.blps_lab2.data;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BankCard")
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
