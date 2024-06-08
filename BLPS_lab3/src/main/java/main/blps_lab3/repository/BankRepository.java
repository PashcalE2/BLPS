package main.blps_lab3.repository;

import main.blps_lab3.model.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BankRepository extends JpaRepository<BankCard, Long> {
    @Modifying
    @Transactional
    @Query(value = "call \"remove_money\"(:cardId, :money)", nativeQuery = true)
    void removeMoney(Long cardId, Integer money);
}
