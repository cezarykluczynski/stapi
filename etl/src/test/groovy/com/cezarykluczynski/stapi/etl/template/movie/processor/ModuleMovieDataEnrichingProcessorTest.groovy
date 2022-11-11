package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import spock.lang.Specification

import java.time.LocalDate

class ModuleMovieDataEnrichingProcessorTest extends Specification {

	protected static final Integer RELEASE_YEAR = 1967
	protected static final Integer RELEASE_MONTH = 9
	protected static final Integer RELEASE_DAY = 22

	ModuleMovieDataEnrichingProcessor moduleEpisodeDataEnrichingProcessor

	void setup() {
		moduleEpisodeDataEnrichingProcessor = new ModuleMovieDataEnrichingProcessor()
	}

	void "enriches with found values"() {
		given:
		MovieTemplate movieTemplate = new MovieTemplate()
		ModuleEpisodeData moduleEpisodeData = new ModuleEpisodeData(
				releaseDay: RELEASE_DAY,
				releaseMonth: RELEASE_MONTH,
				releaseYear: RELEASE_YEAR,
		)
		EnrichablePair<ModuleEpisodeData, MovieTemplate> enrichablePair = EnrichablePair.of(moduleEpisodeData, movieTemplate)

		when:
		moduleEpisodeDataEnrichingProcessor.enrich(enrichablePair)

		then:
		0 * _
		movieTemplate.usReleaseDate == LocalDate.of(RELEASE_YEAR, RELEASE_MONTH, RELEASE_DAY)
	}

	void "tolerates null values"() {
		given:
		MovieTemplate movieTemplate = new MovieTemplate()
		ModuleEpisodeData moduleEpisodeData = new ModuleEpisodeData()
		EnrichablePair<ModuleEpisodeData, MovieTemplate> enrichablePair = EnrichablePair.of(moduleEpisodeData, movieTemplate)

		when:
		moduleEpisodeDataEnrichingProcessor.enrich(enrichablePair)

		then:
		0 * _
		notThrown(Exception)
	}

	void "tolerates null ModuleEpisodeData"() {
		given:
		MovieTemplate movieTemplate = new MovieTemplate()
		EnrichablePair<ModuleEpisodeData, MovieTemplate> enrichablePair = EnrichablePair.of(null, movieTemplate)

		when:
		moduleEpisodeDataEnrichingProcessor.enrich(enrichablePair)

		then:
		0 * _
		notThrown(Exception)
	}

}
