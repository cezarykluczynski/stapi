package com.cezarykluczynski.stapi.etl.common.service.step

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelper
import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelperDumpFormatter
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class StepFrequentHitCachingLoggerTest extends Specification {

	private static final String STEP_NAME = 'STEP_NAME'

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

	void "does not log before step"() {
		when: 'step started callback is called'
		stepFrequentHitCachingLogger.stepStarted(stepExecutionMock)

		then: 'no interactions are expected'
		0 * _
	}

	void "logs after step"() {
		given:
		Map<String, Integer> contents = Map.of('Data', 7)
		Map<MediaWikiSource, Map<String, Integer>> cacheMap = Map.of(MediaWikiSource.MEMORY_ALPHA_EN, contents)

		when: 'step ended callback is called'
		stepFrequentHitCachingLogger.stepEnded(stepExecutionMock)

		then: 'cache statistics are dumped'
		1 * frequentHitCachingHelperMock.dumpStatisticsAndReset() >> cacheMap

		then: 'name is used to build message'
		1 * stepExecutionMock.stepName >> STEP_NAME

		then: 'formatter is called'
		1 * frequentHitCachingHelperDumpFormatterMock.format(cacheMap) >> 'formatted report'

		then: 'no other interactions are expected'
		0 * _
	}

	void "does not log after step is there is nothing to log"() {
		given:
		Map<MediaWikiSource, Map<String, Integer>> cacheMap = Map.of()

		when: 'step ended callback is called'
		stepFrequentHitCachingLogger.stepEnded(stepExecutionMock)

		then: 'cache statistics are dumped'
		1 * frequentHitCachingHelperMock.dumpStatisticsAndReset() >> cacheMap

		then: 'no other interactions are expected'
		0 * _
	}

}
