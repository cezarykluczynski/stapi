package com.cezarykluczynski.stapi.etl.title.creation.service;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Service;

@Service
public class TitleListStepExecutionListener implements StepExecutionListener {

	private final TitleListCache titleListCache;

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
