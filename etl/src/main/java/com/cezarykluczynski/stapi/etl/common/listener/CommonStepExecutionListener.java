package com.cezarykluczynski.stapi.etl.common.listener;

import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelper;
import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelperDumpFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class CommonStepExecutionListener implements StepExecutionListener {

	private final FrequentHitCachingHelper frequentHitCachingHelper;

	private final FrequentHitCachingHelperDumpFormatter frequentHitCachingHelperDumpFormatter;

	@Inject
	public CommonStepExecutionListener(FrequentHitCachingHelper frequentHitCachingHelper,
			FrequentHitCachingHelperDumpFormatter frequentHitCachingHelperDumpFormatter) {
		this.frequentHitCachingHelper = frequentHitCachingHelper;
		this.frequentHitCachingHelperDumpFormatter = frequentHitCachingHelperDumpFormatter;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("Step {} started at {}", stepExecution.getStepName(), stepExecution.getStartTime());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		String stepName = stepExecution.getStepName();
		log.info("Step {} finished at {} with exit code {}, with {} reads, and {} entities to write",
				stepName,
				stepExecution.getLastUpdated(),
				stepExecution.getExitStatus().getExitCode(),
				stepExecution.getReadCount(),
				stepExecution.getWriteCount());
		log.info("FrequentHitCachingHelper after step {}: {}", stepName,
				frequentHitCachingHelperDumpFormatter.format(frequentHitCachingHelper.dumpStatisticsAndReset()));
		return null;
	}

}
