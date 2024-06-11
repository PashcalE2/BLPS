package main.blps_lab3.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Profile("publisher")
@Configuration
public class QuartzConfiguration {
    @Bean
    public JobDetail dailyJobDetail() {
        return JobBuilder.newJob(DailyJob.class)
                .withIdentity("dailyJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger dailyJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(dailyJobDetail())
                .withIdentity("dailyTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
    }

    @Bean
    public JobDetail monthlyJobDetail() {
        return JobBuilder.newJob(MonthlyJob.class)
                .withIdentity("monthlyJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger monthlyJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(monthlyJobDetail())
                .withIdentity("monthlyTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 1 * ?"))
                .build();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(dailyJobDetail(), monthlyJobDetail());
        schedulerFactoryBean.setTriggers(dailyJobTrigger(), monthlyJobTrigger());
        return schedulerFactoryBean;
    }
}
