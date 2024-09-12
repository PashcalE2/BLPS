package main.blps_lab4.repository;

import main.blps_lab4.dto.BankCardInterface;
import main.blps_lab4.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "select * from \"User\"", nativeQuery = true)
    List<UserEntity> getAll();

    @Transactional
    @Modifying
    @Query(value = "insert into \"User\" values (default, :email, :password, cast(:role as \"RoleEnum\"), false)", nativeQuery = true)
    void registerUser(String email, String password, String role);

    @Query(value = "select id, user_id as userId, serial_number as serialNumber, validity_date as validityDate, cvv, money from \"BankCard\" where user_id = :userId", nativeQuery = true)
    Optional<BankCardInterface> findBankCardByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "call \"attach_user_card\"(:userId, :serialNumber, :validityDate, :cvv)", nativeQuery = true)
    void attachClientCard(Long userId, String serialNumber, String validityDate, String cvv);

    @Transactional
    @Modifying
    @Query(value = "update \"User\" set banned = true where id = :userId", nativeQuery = true)
    void banUser(Long userId);

    @Transactional
    @Modifying
    @Query(value = "update \"User\" set banned = false where id = :userId", nativeQuery = true)
    void unbanUser(Long userId);

    @Query(value = "select * from \"User\" where email = :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);
}
