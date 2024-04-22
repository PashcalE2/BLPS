package main.blps_lab1.repository;

import main.blps_lab1.data.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ServerRepository extends JpaRepository<BankCard, Long> {
    @Modifying
    @Transactional
    @Query(value = "call remove_money(:card_serial, :validity_date, :cvv, :money)", nativeQuery = true)
    void removeMoney(String card_serial, String validity_date, String cvv, Integer money);
}
