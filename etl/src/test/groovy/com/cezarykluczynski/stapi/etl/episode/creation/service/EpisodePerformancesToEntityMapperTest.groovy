package com.cezarykluczynski.stapi.etl.episode.creation.service

import com.cezarykluczynski.stapi.etl.common.service.EntityRefreshingLookupByNameService
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.enums.PerformanceType
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
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
	private static final String PERFORMANCE_4_PERFORMER_NAME = 'PERFORMANCE_4_PERFORMER_NAME'
	private static final String PERFORMANCE_4_CHARACTER_NAME = 'PERFORMANCE_4_CHARACTER_NAME'
	private static final String PERFORMANCE_4_PERFORMANCE_TYPE = PerformanceType.PERFORMANCE
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private EntityRefreshingLookupByNameService entityRefreshingLookupByNameServiceMock

	private EpisodePerformancesToEntityMapper episodePerformancesToEntityMapper

	void setup() {
		entityRefreshingLookupByNameServiceMock = Mock()
		episodePerformancesToEntityMapper = new EpisodePerformancesToEntityMapper(entityRefreshingLookupByNameServiceMock)
	}

	void "creates EpisodePerformancesEntitiesDTO and enriches Episode entity"() {
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
		EpisodePerformanceDTO performanceDTOToFilterOut = new EpisodePerformanceDTO(
				performerName: PERFORMANCE_4_PERFORMER_NAME,
				characterName: PERFORMANCE_4_CHARACTER_NAME,
				performanceType: PERFORMANCE_4_PERFORMANCE_TYPE
		)
		Performer performer = new Performer()
		Performer stuntPerformer = new Performer()
		Performer standInPerformer = new Performer()
		Character character = new Character()
		List<EpisodePerformanceDTO> episodePerformances = Lists.newArrayList(performanceDTO, stuntPerformanceDTO, standInPerformanceDTO,
				performanceDTOToFilterOut)

		when:
		EpisodePerformancesEntitiesDTO episodePerformancesEntitiesDTO = episodePerformancesToEntityMapper
				.mapToEntities(episodePerformances, episode)

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMANCE_1_PERFORMER_NAME, SOURCE) >> Optional.of(performer)
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(PERFORMANCE_1_CHARACTER_NAME, SOURCE) >> Optional.of(character)
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMANCE_2_PERFORMER_NAME, SOURCE) >> Optional.of(stuntPerformer)
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMANCE_3_PERFORMER_NAME, SOURCE) >> Optional.of(standInPerformer)
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(PERFORMANCE_4_CHARACTER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMANCE_4_PERFORMER_NAME, SOURCE) >> Optional.empty()
		0 * _
		performer.characters.contains character
		character.performers.contains performer
		episodePerformancesEntitiesDTO.characterSet.contains character
		episodePerformancesEntitiesDTO.performerSet.contains performer
		episode.stuntPerformers.contains stuntPerformer
		episode.standInPerformers.contains standInPerformer
	}

	void "creates empty list of episodes performances is passed, empty EpisodePerformancesEntitiesDTO is returned"() {
		given:
		Episode episode = new Episode()

		when:
		EpisodePerformancesEntitiesDTO episodePerformancesEntitiesDTO = episodePerformancesToEntityMapper.mapToEntities([], episode)

		then:
		0 * _
		episodePerformancesEntitiesDTO.characterSet.empty
		episodePerformancesEntitiesDTO.performerSet.empty
		episode.stuntPerformers.empty
		episode.standInPerformers.empty
	}

}
