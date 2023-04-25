package com.cezarykluczynski.stapi.etl.common.service.step;

import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.cache.FrequentHitCachingHelper;
import com.cezarykluczynski.stapi.etl.mediawiki.cache.FrequentHitCachingHelperDumpFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Map;

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
		final Map<MediaWikiSource, Map<String, Integer>> statisticsDump = frequentHitCachingHelper.dumpStatisticsAndReset();
		if (!statisticsDump.isEmpty()) {
			log.info("FrequentHitCachingHelper after step {}: {}", stepExecution.getStepName(),
					frequentHitCachingHelperDumpFormatter.format(statisticsDump));
		}
	}

}
