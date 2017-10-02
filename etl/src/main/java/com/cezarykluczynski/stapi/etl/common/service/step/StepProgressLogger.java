package com.cezarykluczynski.stapi.etl.common.service.step;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(1)
@Service
@Slf4j
public class StepProgressLogger implements StepLogger {

	public void stepStarted(StepExecution stepExecution) {
		log.info("Step {} started at {}", stepExecution.getStepName(), stepExecution.getStartTime());
	}

	public void stepEnded(StepExecution stepExecution) {
		String stepName = stepExecution.getStepName();
		log.info("Step {} finished at {} with exit code {}, with {} reads, and {} entities to write", stepName, stepExecution.getLastUpdated(),
				stepExecution.getExitStatus().getExitCode(), stepExecution.getReadCount(), stepExecution.getWriteCount());
	}

}
