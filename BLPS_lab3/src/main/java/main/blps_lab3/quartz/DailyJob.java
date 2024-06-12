package main.blps_lab3.quartz;

import main.blps_lab3.service.RequestMetricService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailyJob implements Job {
    @Autowired
    private RequestMetricService requestMetricService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        requestMetricService.saveAllRequestsInfo();
    }
}
