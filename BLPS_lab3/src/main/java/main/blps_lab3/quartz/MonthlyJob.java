package main.blps_lab3.quartz;

import lombok.extern.slf4j.Slf4j;
import main.blps_lab3.dto.RequestsInfoForPeriod;
import main.blps_lab3.model.RequestInfo;
import main.blps_lab3.service.MailService;
import main.blps_lab3.service.RequestMetricService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MonthlyJob implements Job {
    @Autowired
    private RequestMetricService requestMetricService;

    @Autowired
    private MailService mailService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();
        List<RequestsInfoForPeriod> requestInfoList = requestMetricService.getRequestsInfoInRange(now.minusMonths(1L), now);

        String asString = requestInfoList.stream().map((v) -> String.format("%s : %s : %d", v.getRequest(), v.getStatus(), v.getCount()))
                .collect(Collectors.joining("\n"));

        mailService.sendEmailToAdmin(String.format("Ежемесячный отчет по запросам от %s", now), asString);
    }
}
