package com.cezarykluczynski.stapi.etl.common.service.step;

import org.springframework.batch.core.StepExecution;

public interface StepLogger {

	void stepStarted(StepExecution stepExecution);

	void stepEnded(StepExecution stepExecution);

}
