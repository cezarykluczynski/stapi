package com.cezarykluczynski.stapi.etl.common.service.step.diff;

import com.cezarykluczynski.stapi.etl.common.service.step.StepLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StepDiffLogger implements StepLogger {

	private final StepDiffProperties stepDiffProperties;
	private final StepDiffProvider stepDiffProvider;
	private final StepDiffFormatter stepDiffFormatter;

	@Override
	public void stepStarted(StepExecution stepExecution) {
		// do nothing
	}

	@Override
	public void stepEnded(StepExecution stepExecution) {
		if (!stepDiffProperties.isEnabled()) {
			return;
		}

		try {
			log.info("{}", stepDiffFormatter.format(stepDiffProvider.provide(stepExecution)));
		} catch (StepDiffUnavailableException e) {
			// ignore
		}
	}

}
