package main.blps_lab4.delegate.dailyTask;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.service.RequestMetricService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SaveAndClearStatsDelegate implements JavaDelegate {
    private final RequestMetricService requestMetricService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("DailyTask: caught {} requests", requestMetricService.getCollectedMetricsCount());
        requestMetricService.saveAllRequestsInfo();
    }
}
