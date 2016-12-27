package com.cezarykluczynski.stapi.etl.episode.creation.service

import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.google.common.collect.Lists
import spock.lang.Specification

class EpisodePerformancesToEntityMapperTest extends Specification {

	private static final String PERFORMANCE_1_PERFORMER_NAME = 'PERFORMANCE_1_PERFORMER_NAME'
	private static final String PERFORMANCE_1_CHARACTER_NAME = 'PERFORMANCE_1_CHARACTER_NAME'
	private static final String PERFORMANCE_1_PERFORMANCE_TYPE = PerformanceType.PERFORMANCE
	private static final String PERFORMANCE_2_PERFORMER_NAME = 'PERFORMANCE_2_PERFORMER_NAME'
	private static final String PERFORMANCE_2_PERFORMING_FOR = 'PERFORMANCE_2_PERFORMING_FOR'
	private static final String PERFORMANCE_2_PERFORMANCE_TYPE = PerformanceType.STUNT
	private static final String PERFORMANCE_3_PERFORMER_NAME = 'PERFORMANCE_3_PERFORMER_NAME'
	private static final String PERFORMANCE_3_PERFORMING_FOR = 'PERFORMANCE_3_PERFORMING_FOR'
	private static final String PERFORMANCE_3_PERFORMANCE_TYPE = PerformanceType.STAND_IN

	private CharacterRepository characterRepositoryMock

	private PerformerRepository performerRepositoryMock

	private PageApi pageApiMock

	private EpisodePerformancesToEntityMapper episodePerformancesToEntityMapper

	def setup() {
		characterRepositoryMock = Mock(CharacterRepository)
		performerRepositoryMock = Mock(PerformerRepository)
		pageApiMock = Mock(PageApi)
		episodePerformancesToEntityMapper = new EpisodePerformancesToEntityMapper(characterRepositoryMock,
				performerRepositoryMock, pageApiMock)
	}

	def "creates EpisodePerformancesEntitiesDTO and enriches Episode entity"() {
		given:
		Episode episode = new Episode()
		EpisodePerformanceDTO performanceDTO = new EpisodePerformanceDTO(
				performerName: PERFORMANCE_1_PERFORMER_NAME,
				characterName: PERFORMANCE_1_CHARACTER_NAME,
				performanceType: PERFORMANCE_1_PERFORMANCE_TYPE
		)
		EpisodePerformanceDTO stuntPerformanceDTO = new EpisodePerformanceDTO(
				performerName: PERFORMANCE_2_PERFORMER_NAME,
				performingFor: PERFORMANCE_2_PERFORMING_FOR,
				performanceType: PERFORMANCE_2_PERFORMANCE_TYPE
		)
		EpisodePerformanceDTO standInPerformanceDTO = new EpisodePerformanceDTO(
				performerName: PERFORMANCE_3_PERFORMER_NAME,
				performingFor: PERFORMANCE_3_PERFORMING_FOR,
				performanceType: PERFORMANCE_3_PERFORMANCE_TYPE
		)
		Performer performer = new Performer()
		Performer stuntPerformer = new Performer()
		Performer standInPerformer = new Performer()
		Character character = new Character()
		List<EpisodePerformanceDTO> episodePerformances = Lists.newArrayList(
				performanceDTO,
				stuntPerformanceDTO,
				standInPerformanceDTO
		)

		when:
		EpisodePerformancesEntitiesDTO episodePerformancesEntitiesDTO = episodePerformancesToEntityMapper
				.mapToEntities(episodePerformances, episode)

		then:
		1 * performerRepositoryMock.findByName(PERFORMANCE_1_PERFORMER_NAME) >> Optional.of(performer)
		1 * characterRepositoryMock.findByName(PERFORMANCE_1_CHARACTER_NAME) >> Optional.of(character)
		1 * performerRepositoryMock.findByName(PERFORMANCE_2_PERFORMER_NAME) >> Optional.of(stuntPerformer)
		1 * performerRepositoryMock.findByName(PERFORMANCE_3_PERFORMER_NAME) >> Optional.of(standInPerformer)
		performer.characters.contains character
		character.performers.contains performer
		episodePerformancesEntitiesDTO.characterSet.contains character
		episodePerformancesEntitiesDTO.performerSet.contains performer
		episode.stuntPerformers.contains stuntPerformer
		episode.standInPerformers.contains standInPerformer
	}

}
