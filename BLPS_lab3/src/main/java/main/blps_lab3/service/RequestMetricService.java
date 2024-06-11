package main.blps_lab3.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.model.RequestInfo;
import main.blps_lab3.repository.RequestInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScope
@Service
@Slf4j
@RequiredArgsConstructor
public class RequestMetricService {
    private final Map<String, Map<String, Long>> metricMap = new ConcurrentHashMap<>();
    private final RequestInfoRepository requestInfoRepository;

    @Transactional
    public void saveAllRequestsInfo() {
        try {
            metricMap.forEach((request, statusMap) -> statusMap.forEach((status, count) -> {
                RequestInfo requestInfo = new RequestInfo();
                requestInfo.setRequest(request);
                requestInfo.setStatus(status);
                requestInfo.setCount(count);
                requestInfo.setDate(LocalDateTime.now());

                requestInfoRepository.save(requestInfo);
            }));
        }
        catch (RuntimeException e) {
            log.error(e.getMessage());
        }

        clearRequestInfoMap();
    }

    private void clearRequestInfoMap() {
        metricMap.clear();
    }

    public List<RequestInfo> getRequestsInfoInRange(LocalDateTime start, LocalDateTime stop) {
        return requestInfoRepository.findInRange(start, stop);
    }

    public void addRequestInfo(String request, String status) {
        Map<String, Long> statusMap = metricMap.get(request);
        if (statusMap == null) {
            statusMap = new ConcurrentHashMap<>();
        }

        Long count = statusMap.get(status);
        if (count == null) {
            count = 1L;
        }
        else {
            count++;
        }

        statusMap.put(status, count);
        metricMap.put(request, statusMap);
    }
}