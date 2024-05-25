package main.blps_lab2.repository;

import main.blps_lab2.data.BankCard;
import main.blps_lab2.data.RoleEnum;
import main.blps_lab2.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into \"User\" values (default, :email, :password, cast(:role as \"RoleEnum\"), false)", nativeQuery = true)
    void registerUser(String email, String password, String role);

    @Query(value = "select id, user_id as userId, serial_number as serialNumber, validity_date as validityDate, cvv, money from \"BankCard\" where user_id = :userId", nativeQuery = true)
    Optional<BankCard> findBankCardByUserId(Long userId);

    @Modifying
    @Query(value = "call \"attach_user_card\"(:userId, :serialNumber, :validityDate, :cvv)", nativeQuery = true)
    void attachClientCard(Long userId, String serialNumber, String validityDate, String cvv);

    @Modifying
    @Query(value = "update \"User\" set banned = true where id = :userId", nativeQuery = true)
    void banUser(Long userId);

    @Modifying
    @Query(value = "update \"User\" set banned = false where id = :userId", nativeQuery = true)
    void unbanUser(Long userId);

    @Query(value = "select * from \"User\" where email = :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);
}
