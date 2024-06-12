package main.blps_lab3.repository;

import main.blps_lab3.model.RequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {
    @Query(value = "select * from \"RequestInfo\" where date >= :start and date <= :stop", nativeQuery = true)
    List<RequestInfo> findInRange(LocalDateTime start, LocalDateTime stop);

    @Transactional
    @Modifying
    @Query(value = "insert into \"RequestInfo\" values (default, :request, :status, :count, :date)", nativeQuery = true)
    void insert(String request, String status, Long count, LocalDateTime date);
}
