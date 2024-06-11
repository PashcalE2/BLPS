package main.blps_lab3.quartz;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import main.blps_lab3.model.RequestInfo;
import main.blps_lab3.service.MailService;
import main.blps_lab3.service.RequestMetricService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Profile("publisher")
@Component
@RequiredArgsConstructor
public class MonthlyJob implements Job {
    private final RequestMetricService requestMetricService;
    private final MailService mailService;
    @Value("${admin.email}")
    private String adminEmail;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime now = LocalDateTime.now();
        List<RequestInfo> requestInfoList = requestMetricService.getRequestsInfoInRange(now.minusMonths(1L), now);

        mailService.sendEmail(
                adminEmail,
                String.format("Отчет по запросам от %s", now),
                requestInfoList.stream().map(Object::toString)
                        .collect(Collectors.joining("\n"))
        );
    }
}
