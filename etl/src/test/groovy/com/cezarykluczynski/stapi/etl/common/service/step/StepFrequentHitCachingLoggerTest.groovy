package com.cezarykluczynski.stapi.etl.common.service.step

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelper
import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelperDumpFormatter
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class StepFrequentHitCachingLoggerTest extends Specification {

	private FrequentHitCachingHelper frequentHitCachingHelperMock

	private FrequentHitCachingHelperDumpFormatter frequentHitCachingHelperDumpFormatterMock

	private StepExecution stepExecutionMock

	private StepFrequentHitCachingLogger stepFrequentHitCachingLogger

	void setup() {
		frequentHitCachingHelperMock = Mock()
		frequentHitCachingHelperDumpFormatterMock = Mock()
		stepExecutionMock = Mock()
		stepFrequentHitCachingLogger = new StepFrequentHitCachingLogger(frequentHitCachingHelperMock, frequentHitCachingHelperDumpFormatterMock)
	}

	void "logs before step"() {
		when: 'step started callback is called'
		stepFrequentHitCachingLogger.stepStarted(stepExecutionMock)

		then: 'no interactions are expected'
		0 * _
	}

	void "logs after step"() {
		given:
		Map<MediaWikiSource, Map<String, Integer>> cacheMap = Mock()

		when: 'step ended callback is called'
		stepFrequentHitCachingLogger.stepEnded(stepExecutionMock)

		then: 'name  is used to build message'
		1 * stepExecutionMock.stepName

		then: 'cache statistics are dumped'
		1 * frequentHitCachingHelperMock.dumpStatisticsAndReset() >> cacheMap
		1 * frequentHitCachingHelperDumpFormatterMock.format(cacheMap)

		then: 'no other interactions are expected'
		0 * _
	}

}
