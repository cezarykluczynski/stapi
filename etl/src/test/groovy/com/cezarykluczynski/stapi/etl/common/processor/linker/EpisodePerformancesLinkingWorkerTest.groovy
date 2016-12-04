package com.cezarykluczynski.stapi.etl.common.processor.linker

import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO
import com.cezarykluczynski.stapi.etl.template.common.linker.EpisodePerformancesLinkingWorker
import com.cezarykluczynski.stapi.etl.template.common.service.EpisodePerformancesExtractor
import com.cezarykluczynski.stapi.etl.template.common.service.EpisodePerformancesToEntityMapper
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class EpisodePerformancesLinkingWorkerTest extends Specification {

	private CharacterRepository characterRepositoryMock

	private PerformerRepository performerRepositoryMock

	private EpisodePerformancesExtractor episodePerformancesExtractorMock

	private EpisodePerformancesToEntityMapper episodePerformancesToEntityMapperMock

	private EpisodePerformancesLinkingWorker episodePerformancesLinkingProcessor

	def setup() {
		characterRepositoryMock = Mock(CharacterRepository)
		performerRepositoryMock = Mock(PerformerRepository)
		episodePerformancesExtractorMock = Mock(EpisodePerformancesExtractor)
		episodePerformancesToEntityMapperMock = Mock(EpisodePerformancesToEntityMapper)
		episodePerformancesLinkingProcessor = new EpisodePerformancesLinkingWorker(characterRepositoryMock,
				performerRepositoryMock, episodePerformancesExtractorMock, episodePerformancesToEntityMapperMock)
	}

	def "when page has category episode, EpisodePerformancesExtractor is called"() {
		given:
		List<EpisodePerformanceDTO> episodePerformanceDTOList = Mock(List)
		Set<Character> charactersSet = Mock(Set)
		Set<Performer> performersSet = Mock(Set)
		EpisodePerformancesEntitiesDTO episodePerformancesEntitiesDTO = Mock(EpisodePerformancesEntitiesDTO)
		Episode episode = Mock(Episode)
		Page page = new Page(
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES)
				)
		)

		when:
		episodePerformancesLinkingProcessor.link(page, episode)

		then:
		1 * episodePerformancesExtractorMock.getEpisodePerformances(page) >> episodePerformanceDTOList
		1 * episodePerformancesToEntityMapperMock.mapToEntities(episodePerformanceDTOList, episode) >> episodePerformancesEntitiesDTO
		1 * episodePerformancesEntitiesDTO.getCharacterSet() >> charactersSet
		1 * characterRepositoryMock.save(charactersSet)
		1 * episodePerformancesEntitiesDTO.getPerformerSet() >> performersSet
		1 * performerRepositoryMock.save(performersSet)
	}

}
