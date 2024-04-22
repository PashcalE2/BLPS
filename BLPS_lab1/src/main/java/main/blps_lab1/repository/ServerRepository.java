package main.blps_lab1.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository {
    @Modifying
    @Query(value = "update BankCard set money = money - :money where serial_number = :card_serial and validity_date = :validity_date and cvv = :cvv", nativeQuery = true)
    void removeMoney(String card_serial, String validity_date, String cvv, Integer money);
}
