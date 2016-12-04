package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.episode.creation.service.SeriesToEpisodeBindingService
import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodePerformancesLinkingWorker
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class ToEpisodeTemplateProcessorTest extends Specification {

	private static final Long PAGE_ID = 1L
	private static final String PAGE_TITLE = 'All Good Things... (episode)'
	private static final String EPISODE_TITLE = 'All Good Things...'

	private EpisodePerformancesLinkingWorker episodePerformancesLinkingWorkerMock

	private PageBindingService pageBindingServiceMock

	private SeriesToEpisodeBindingService seriesToEpisodeBindingServiceMock

	private ToEpisodeTemplateProcessor toEpisodeTemplateProcessor

	def setup() {
		episodePerformancesLinkingWorkerMock = Mock(EpisodePerformancesLinkingWorker)
		pageBindingServiceMock = Mock(PageBindingService)
		seriesToEpisodeBindingServiceMock = Mock(SeriesToEpisodeBindingService)
		toEpisodeTemplateProcessor = new ToEpisodeTemplateProcessor(episodePerformancesLinkingWorkerMock,
				pageBindingServiceMock, seriesToEpisodeBindingServiceMock)
	}


	def "does not interact with dependencies when page does not have episode category"() {
		when:
		toEpisodeTemplateProcessor.process(new Page())

		then:
		0 * _
	}

	def "does not interact with dependencies when page does have episode category, but the production lists category too"() {
		when:
		toEpisodeTemplateProcessor.process(new Page(
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES),
						new CategoryHeader(title: CategoryName.PRODUCTION_LISTS)
				)
		))

		then:
		0 * _
	}

	def "passes page to EpisodePerformancesLinkingWorker"() {
		given:
		Page page = new Page(
				pageId: PAGE_ID,
				title: PAGE_TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES)
				)
		)

		when:
		toEpisodeTemplateProcessor.process(page)

		then:
		1 * episodePerformancesLinkingWorkerMock.link(page, _ as Episode)

	}

}
