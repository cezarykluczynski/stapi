package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData
import com.cezarykluczynski.stapi.etl.episode.creation.service.SeriesToEpisodeBindingService
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.model.series.entity.Series
import spock.lang.Specification

import java.time.LocalDate

class ModuleEpisodeDataEnrichingProcessorTest extends Specification {

	private static final String SERIES_ABBREVIATION = 'TOS'
	private static final Integer SEASON_NUMBER = 2
	private static final Integer EPISODE_NUMBER = 4
	protected static final Integer RELEASE_YEAR = 1967
	protected static final Integer RELEASE_MONTH = 9
	protected static final Integer RELEASE_DAY = 22
	protected static final String PRODUCTION_SERIAL_NUMBER = '60333'

	SeasonRepository seasonRepositoryMock

	SeriesToEpisodeBindingService seriesToEpisodeBindingServiceMock

	ModuleEpisodeDataEnrichingProcessor moduleEpisodeDataEnrichingProcessor

	void setup() {
		seasonRepositoryMock = Mock()
		seriesToEpisodeBindingServiceMock = Mock()
		moduleEpisodeDataEnrichingProcessor = new ModuleEpisodeDataEnrichingProcessor(seasonRepositoryMock, seriesToEpisodeBindingServiceMock)
	}

	void "enriches with found values"() {
		given:
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
		ModuleEpisodeData moduleEpisodeData = new ModuleEpisodeData(
				series: SERIES_ABBREVIATION,
				seasonNumber: SEASON_NUMBER,
				episodeNumber: EPISODE_NUMBER,
				releaseDay: RELEASE_DAY,
				releaseMonth: RELEASE_MONTH,
				releaseYear: RELEASE_YEAR,
				productionNumber: PRODUCTION_SERIAL_NUMBER
		)
		Series series = Mock()
		Season season = Mock()
		EnrichablePair<ModuleEpisodeData, EpisodeTemplate> enrichablePair = EnrichablePair.of(moduleEpisodeData, episodeTemplate)

		when:
		moduleEpisodeDataEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * seriesToEpisodeBindingServiceMock.mapAbbreviationToSeries(SERIES_ABBREVIATION) >> series
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(SERIES_ABBREVIATION, SEASON_NUMBER) >> season
		0 * _
		episodeTemplate.series == series
		episodeTemplate.season == season
		episodeTemplate.seasonNumber == SEASON_NUMBER
		episodeTemplate.episodeNumber == EPISODE_NUMBER
		episodeTemplate.usAirDate == LocalDate.of(RELEASE_YEAR, RELEASE_MONTH, RELEASE_DAY)
		episodeTemplate.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
	}

	void "tolerates null values"() {
		given:
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
		ModuleEpisodeData moduleEpisodeData = new ModuleEpisodeData()
		EnrichablePair<ModuleEpisodeData, EpisodeTemplate> enrichablePair = EnrichablePair.of(moduleEpisodeData, episodeTemplate)

		when:
		moduleEpisodeDataEnrichingProcessor.enrich(enrichablePair)

		then:
		0 * _
		notThrown(Exception)
	}

	void "tolerates null ModuleEpisodeData"() {
		given:
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
		EnrichablePair<ModuleEpisodeData, EpisodeTemplate> enrichablePair = EnrichablePair.of(null, episodeTemplate)

		when:
		moduleEpisodeDataEnrichingProcessor.enrich(enrichablePair)

		then:
		0 * _
		notThrown(Exception)
	}

	void "fixes 'The Cage' values"() {
		given:
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
		ModuleEpisodeData moduleEpisodeData = new ModuleEpisodeData(
				series: SERIES_ABBREVIATION,
				seasonNumber: 0,
				episodeNumber: 1
		)
		Series series = Mock()
		Season season = Mock()
		EnrichablePair<ModuleEpisodeData, EpisodeTemplate> enrichablePair = EnrichablePair.of(moduleEpisodeData, episodeTemplate)

		when:
		moduleEpisodeDataEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * seriesToEpisodeBindingServiceMock.mapAbbreviationToSeries(SERIES_ABBREVIATION) >> series
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(SERIES_ABBREVIATION, 1) >> season
		0 * _
		episodeTemplate.series == series
		episodeTemplate.season == season
		episodeTemplate.seasonNumber == 1
		episodeTemplate.episodeNumber == 0
	}

}
