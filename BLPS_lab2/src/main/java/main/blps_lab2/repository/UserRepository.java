package main.blps_lab2.repository;

import main.blps_lab2.data.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "insert into \"User\" values (default, :email, :password, :role)", nativeQuery = true)
    void registerUser(String email, String password, RoleEnum role);

    @Query(value = "select * from \"User\" where email=:email and password=:password", nativeQuery = true)
    Optional<User> findUserByEmailAndPassword(String email, String password);

    @Query(value = "select id, user_id as userId, serial_number as serialNumber, validity_date as validityDate, cvv, money from \"BankCard\" where user_id = :userId", nativeQuery = true)
    Optional<BankCard> findBankCardByUserId(Long userId);

    @Modifying
    @Query(value = "call \"attach_user_card\"(:userId, :serialNumber, :validityDate, :cvv)", nativeQuery = true)
    void attachClientCard(Long userId, String serialNumber, String validityDate, String cvv);

    Optional<User> findUserByEmail(String email);
}
