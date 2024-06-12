package main.blps_lab3.quartz;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Profile("publisher")
@Configuration
@RequiredArgsConstructor
public class QuartzConfiguration {
    private final ApplicationContext applicationContext;

    @Bean
    public JobFactory jobFactory() {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory());
        factory.setJobDetails(dailyJobDetail(), monthlyJobDetail());
        factory.setTriggers(dailyJobTrigger(), monthlyJobTrigger());
        return factory;
    }

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
}
