package com.cezarykluczynski.stapi.etl.common.listener

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelper
import com.cezarykluczynski.stapi.sources.mediawiki.cache.FrequentHitCachingHelperDumpFormatter
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import spock.lang.Specification

class CommonStepExecutionListenerTest extends Specification {

	private FrequentHitCachingHelper frequentHitCachingHelperMock

	private FrequentHitCachingHelperDumpFormatter frequentHitCachingHelperDumpFormatterMock

	private StepExecution stepExecutionMock

	private CommonStepExecutionListener commonStepExecutionListener

	def setup() {
		frequentHitCachingHelperMock = Mock(FrequentHitCachingHelper)
		frequentHitCachingHelperDumpFormatterMock = Mock(FrequentHitCachingHelperDumpFormatter)
		stepExecutionMock = Mock(StepExecution)
		commonStepExecutionListener = new CommonStepExecutionListener(frequentHitCachingHelperMock,
				frequentHitCachingHelperDumpFormatterMock)
	}

	def "logs before step"() {
		when: "before step callback is called"
		commonStepExecutionListener.beforeStep(stepExecutionMock)

		then: 'name and start time is used to build message'
		1 * stepExecutionMock.getStepName()
		1 * stepExecutionMock.getStartTime()

		then: 'no other interactions are expected'
		0 * _
	}

	def "logs after step"() {
		given:
		ExitStatus exitStatusMock = Mock(ExitStatus)
		Map<MediaWikiSource, Map<String, Integer>> cacheMap = Mock(Map)

		when: 'after step callback is called'
		commonStepExecutionListener.afterStep(stepExecutionMock)

		then: 'name, last update time, exit code, read count, and write count is used to build message'
		1 * stepExecutionMock.getStepName()
		1 * stepExecutionMock.getLastUpdated()
		1 * stepExecutionMock.getExitStatus() >> exitStatusMock
		1 * exitStatusMock.getExitCode()
		1 * stepExecutionMock.getReadCount()
		1 * stepExecutionMock.getWriteCount()

		then: 'cache statistics are dumped'
		1 * frequentHitCachingHelperMock.dumpStatisticsAndReset() >> cacheMap
		1 * frequentHitCachingHelperDumpFormatterMock.format(cacheMap)

		then: 'no other interactions are expected'
		0 * _
	}

}
