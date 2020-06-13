package com.bettorleague.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BatchLauncher {
    private final List<String> competitions = List.of("2002", "2014", "2015", "2019", "2021", "2003", "2013");

    private final Job footballDataJob;
    private final JobLauncher jobLauncher;

    public BatchLauncher(JobLauncher jobLauncher,
                         Job footballDataJob) {
        this.jobLauncher = jobLauncher;
        this.footballDataJob = footballDataJob;
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void launchCompetitionJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        for (String competition : competitions) {
            jobLauncher.run(footballDataJob, newExecution(competition));
            this.pause();
        }
    }

    private JobParameters newExecution(String competitionId) {
        Map<String, JobParameter> parameters = new HashMap<>();
        JobParameter competition = new JobParameter(competitionId);
        parameters.put("competitionId", competition);
        return new JobParameters(parameters);
    }

    private void pause() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
    }
}
