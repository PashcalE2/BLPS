package main.blps_lab4.repository;

import main.blps_lab4.dto.RequestsInfoForPeriod;
import main.blps_lab4.model.RequestInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {
    @Query(value = "select request, status, sum(count) as count from \"RequestInfo\" where date >= :start and date <= :stop group by request, status", nativeQuery = true)
    List<RequestsInfoForPeriod> findInRange(LocalDateTime start, LocalDateTime stop);

    @Transactional
    @Modifying
    @Query(value = "insert into \"RequestInfo\" values (default, :request, :status, :count, :date)", nativeQuery = true)
    void insert(String request, String status, Long count, LocalDateTime date);
}
