package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.template.common.processor.linker.EpisodePerformancesLinkingWorker
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class ToEpisodeTemplateProcessorTest extends Specification {

	private static final Long PAGE_ID = 1L

	private EpisodePerformancesLinkingWorker episodePerformancesLinkingWorkerMock

	private ToEpisodeTemplateProcessor toEpisodeTemplateProcessor

	def setup() {
		episodePerformancesLinkingWorkerMock = Mock(EpisodePerformancesLinkingWorker)
		toEpisodeTemplateProcessor = new ToEpisodeTemplateProcessor(episodePerformancesLinkingWorkerMock)
	}

	def "passes page to EpisodePerformancesLinkingWorker"() {
		given:
		Page page = new Page(
				pageId: PAGE_ID
		)

		when:
		toEpisodeTemplateProcessor.process(page)

		then:
		episodePerformancesLinkingWorkerMock.link(page)

	}

}
