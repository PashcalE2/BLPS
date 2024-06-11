package main.blps_lab3.quartz;

import lombok.RequiredArgsConstructor;
import main.blps_lab3.service.RequestMetricService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyJob implements Job {
    private final RequestMetricService requestMetricService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        requestMetricService.saveAllRequestsInfo();
    }
}
