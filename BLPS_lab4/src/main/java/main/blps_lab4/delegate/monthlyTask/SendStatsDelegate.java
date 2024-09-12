package main.blps_lab4.delegate.monthlyTask;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.blps_lab4.dto.RequestsInfoForPeriod;
import main.blps_lab4.service.MailService;
import main.blps_lab4.service.RequestMetricService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class SendStatsDelegate implements JavaDelegate {
    private final RequestMetricService requestMetricService;
    private final MailService mailService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        List<RequestsInfoForPeriod> requestInfoList = requestMetricService.getRequestsInfoInRange(now.minusMonths(1L), now);

        String asString = requestInfoList.stream().map((v) -> String.format("%s : %s : %d", v.getRequest(), v.getStatus(), v.getCount()))
                .collect(Collectors.joining("\n"));

        mailService.sendEmailToAdmin(String.format("Ежемесячный отчет по запросам от %s", now), asString);
    }
}
