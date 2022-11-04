package com.cezarykluczynski.stapi.etl.episode.creation.service

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import spock.lang.Specification

class SeriesToEpisodeBindingServiceTest extends Specification {

	private static final String TOS = 'TOS'

	private SeriesRepository seriesRepositoryMock

	private SeriesToEpisodeBindingService seriesToEpisodeBindingService

	void setup() {
		seriesRepositoryMock = Mock()
		seriesToEpisodeBindingService = new SeriesToEpisodeBindingService(seriesRepositoryMock)
	}

	void "gets series from abbreviation"() {
		given:
		Series seriesTos = new Series(abbreviation: TOS)

		when:
		Series seriesOutput = seriesToEpisodeBindingService.mapAbbreviationToSeries(TOS)

		then:
		1 * seriesRepositoryMock.findByAbbreviation(TOS) >> Optional.of(seriesTos)
		seriesOutput == seriesTos
	}

	void "gets null for unknown abbreviation"() {
		when:
		Series seriesOutput = seriesToEpisodeBindingService.mapAbbreviationToSeries(TOS)

		then:
		1 * seriesRepositoryMock.findByAbbreviation(TOS) >> Optional.empty()
		seriesOutput == null
	}

}
