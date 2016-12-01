package com.cezarykluczynski.stapi.etl.common.processor.linker

import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodePerformancesLinkingWorker
import com.cezarykluczynski.stapi.etl.template.common.service.EpisodePerformancesExtractor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class EpisodePerformancesLinkingWorkerTest extends Specification {

	private CharacterRepository characterRepositoryMock

	private PerformerRepository performerRepositoryMock

	private EpisodePerformancesExtractor episodePerformancesExtractorMock

	private EpisodePerformancesLinkingWorker episodePerformancesLinkingProcessor

	def setup() {
		characterRepositoryMock = Mock(CharacterRepository)
		performerRepositoryMock = Mock(PerformerRepository)
		episodePerformancesExtractorMock = Mock(EpisodePerformancesExtractor)
		episodePerformancesLinkingProcessor = new EpisodePerformancesLinkingWorker(characterRepositoryMock,
				performerRepositoryMock, episodePerformancesExtractorMock)
	}

	def "does not interact with repositories when page does not have episode category"() {
		when:
		episodePerformancesLinkingProcessor.link(new Page())

		then:
		0 * _
	}

	def "does not interact with repositories when page does have episode category, but the production lists category too"() {
		when:
		episodePerformancesLinkingProcessor.link(new Page(
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES),
						new CategoryHeader(title: CategoryName.PRODUCTION_LISTS)
				)
		))

		then:
		0 * _
	}

	def "when page has category episode, EpisodePerformancesExtractor is called"() {
		given:
		Page page = new Page(
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES)
				)
		)

		when:
		episodePerformancesLinkingProcessor.link(page)

		then:
		1 * episodePerformancesExtractorMock.getEpisodePerformances(page)
		0 * _
	}

}
