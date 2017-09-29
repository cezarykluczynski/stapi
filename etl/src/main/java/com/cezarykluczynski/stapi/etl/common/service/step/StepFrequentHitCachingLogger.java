package com.cezarykluczynski.stapi.etl.common.service.step;

import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelper;
import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelperDumpFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(2)
@Service
@Slf4j
public class StepFrequentHitCachingLogger implements StepLogger {

	private final FrequentHitCachingHelper frequentHitCachingHelper;

	private final FrequentHitCachingHelperDumpFormatter frequentHitCachingHelperDumpFormatter;

	public StepFrequentHitCachingLogger(FrequentHitCachingHelper frequentHitCachingHelper,
			FrequentHitCachingHelperDumpFormatter frequentHitCachingHelperDumpFormatter) {
		this.frequentHitCachingHelper = frequentHitCachingHelper;
		this.frequentHitCachingHelperDumpFormatter = frequentHitCachingHelperDumpFormatter;
	}

	public void stepStarted(StepExecution stepExecution) {
		// do nothing
	}

	public void stepEnded(StepExecution stepExecution) {
		log.info("FrequentHitCachingHelper after step {}: {}", stepExecution.getStepName(),
				frequentHitCachingHelperDumpFormatter.format(frequentHitCachingHelper.dumpStatisticsAndReset()));
	}

}
