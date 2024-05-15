package main.blps_lab2.repository;

import main.blps_lab2.data.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BankRepository extends JpaRepository<BankCard, Long> {
    @Modifying
    @Transactional
    @Query(value = "call \"remove_money\"(:cardId, :money)", nativeQuery = true)
    void removeMoney(Long cardId, Integer money);
}
