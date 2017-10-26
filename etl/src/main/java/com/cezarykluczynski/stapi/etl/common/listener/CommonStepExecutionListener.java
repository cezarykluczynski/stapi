package com.cezarykluczynski.stapi.etl.common.listener;

import com.cezarykluczynski.stapi.etl.common.service.step.StepLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommonStepExecutionListener implements StepExecutionListener {

	private final List<StepLogger> stepLoggerList;

	public CommonStepExecutionListener(List<StepLogger> stepLoggerList) {
		this.stepLoggerList = stepLoggerList;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		stepLoggerList.forEach(stepLogger -> stepLogger.stepStarted(stepExecution));
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		stepLoggerList.forEach(stepLogger -> stepLogger.stepEnded(stepExecution));
		return null;
	}

}
