package com.cezarykluczynski.stapi.etl.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.StepListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommonStepExecutionListener implements StepExecutionListener, StepListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("Step {} started at {}", stepExecution.getStepName(), stepExecution.getStartTime());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("Step {} finished at {} with exit code {}, with {} reads, and {} entities to write",
				stepExecution.getStepName(),
				stepExecution.getLastUpdated(),
				stepExecution.getExitStatus().getExitCode(),
				stepExecution.getReadCount(),
				stepExecution.getWriteCount());
		return null;
	}

}
