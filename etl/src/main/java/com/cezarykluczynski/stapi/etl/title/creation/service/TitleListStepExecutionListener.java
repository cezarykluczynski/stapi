package com.cezarykluczynski.stapi.etl.title.creation.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class TitleListStepExecutionListener implements StepExecutionListener {

	private final TitleListCache titleListCache;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public TitleListStepExecutionListener(TitleListCache titleListCache) {
		this.titleListCache = titleListCache;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// do nothing
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		titleListCache.produceTitlesFromListPages();
		return null;
	}
}
