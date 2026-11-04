package com.srilatha.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/** Logs read / filtered / written / skipped counts when the job finishes. */
@Component
public class JobSummaryListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobSummaryListener.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        for (StepExecution s : jobExecution.getStepExecutions()) {
            log.info("Step '{}' finished: read={}, filtered={}, written={}, skipped={}",
                    s.getStepName(), s.getReadCount(), s.getFilterCount(),
                    s.getWriteCount(), s.getSkipCount());
        }
        log.info("Job status: {}", jobExecution.getStatus());
    }
}
