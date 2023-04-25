package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.google.common.collect.Sets
import spock.lang.Specification

class VideoTemplateContentsFixedValueEnricherTest extends Specification {

	private SeriesRepository seriesRepositoryMock

	private SeasonRepository seasonRepositoryMock

	private MovieRepository movieRepositoryMock

	private VideoTemplateContentsFixedValueEnricher videoTemplateContentsFixedValueEnricher

	void setup() {
		seriesRepositoryMock = Mock()
		seasonRepositoryMock = Mock()
		movieRepositoryMock = Mock()
		videoTemplateContentsFixedValueEnricher = new VideoTemplateContentsFixedValueEnricher(seriesRepositoryMock, seasonRepositoryMock,
				movieRepositoryMock)
	}

	void "ignores non-tracked title"() {
		given:
		Page page = new Page(title: 'Star Trek: Discovery (DVD)')
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFixedValueEnricher.enrich(enrichablePair)

		then:
		videoTemplate.series.empty
		videoTemplate.seasons.empty
		videoTemplate.movies.empty
	}

	void "makes content empty"() {
		given:
		Page page = new Page(title: 'Sense of Scale')
		VideoTemplate videoTemplate = new VideoTemplate(
				series: Sets.newHashSet(Mock(Series)),
				seasons: Sets.newHashSet(Mock(Season)),
				movies: Sets.newHashSet(Mock(Movie)),
		)
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFixedValueEnricher.enrich(enrichablePair)

		then:
		videoTemplate.series.empty
		videoTemplate.seasons.empty
		videoTemplate.movies.empty
	}

	void "makes content empty, then adds all seasons and series"() {
		given:
		Series tos = Mock()
		Season tosSeason1 = Mock()
		Season tosSeason2 = Mock()
		Season tosSeason3 = Mock()
		Page page = new Page(title: 'Star Trek: The Original Series (VHS)')
		VideoTemplate videoTemplate = new VideoTemplate(
				series: Sets.newHashSet(Mock(Series)),
				seasons: Sets.newHashSet(Mock(Season)),
				movies: Sets.newHashSet(Mock(Movie)),
		)
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFixedValueEnricher.enrich(enrichablePair)

		then:
		1 * seriesRepositoryMock.findByAbbreviation('TOS') >> Optional.of(tos)
		1 * seasonRepositoryMock.findBySeriesAbbreviation('TOS') >> [tosSeason1, tosSeason2, tosSeason3]
		0 * _
		videoTemplate.series == Set.of(tos)
		videoTemplate.seasons == Set.of(tosSeason1, tosSeason2, tosSeason3)
		videoTemplate.movies.empty
	}

	void "adds series"() {
		given:
		Series voy = Mock()
		Page page = new Page(title: 'Star Trek - The Seven of Nine Collection')
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFixedValueEnricher.enrich(enrichablePair)

		then:
		1 * seriesRepositoryMock.findByAbbreviation('VOY') >> Optional.of(voy)
		0 * _
		videoTemplate.series == Set.of(voy)
		videoTemplate.seasons.empty
		videoTemplate.movies.empty
	}

	void "adds exact seasons"() {
		given:
		Season tngSeason1 = Mock()
		Season tngSeason2 = Mock()
		Page page = new Page(title: 'Star Trek: The Next Generation (Betamax)')
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFixedValueEnricher.enrich(enrichablePair)

		then:
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber('TNG', 1) >> tngSeason1
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber('TNG', 2) >> tngSeason2
		0 * _
		videoTemplate.series.empty
		videoTemplate.seasons == Set.of(tngSeason1, tngSeason2)
		videoTemplate.movies.empty
	}

	void "adds all seasons"() {
		given:
		Season tngSeason1 = Mock()
		Season tngSeason2 = Mock()
		Season tngSeason3 = Mock()
		Season tngSeason4 = Mock()
		Season tngSeason5 = Mock()
		Season tngSeason6 = Mock()
		Season tngSeason7 = Mock()
		Page page = new Page(title: 'Star Trek: The Next Generation (VHS)')
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFixedValueEnricher.enrich(enrichablePair)

		then:
		1 * seasonRepositoryMock
				.findBySeriesAbbreviation('TNG') >> [tngSeason1, tngSeason2, tngSeason3, tngSeason4, tngSeason5, tngSeason6, tngSeason7]
		0 * _
		videoTemplate.series.empty
		videoTemplate.seasons == Set.of(tngSeason1, tngSeason2, tngSeason3, tngSeason4, tngSeason5, tngSeason6, tngSeason7)
		videoTemplate.movies.empty
	}

	void "adds movies"() {
		given:
		Movie theMotionPicture = Mock()
		Movie theWrathOfKhan = Mock()
		Page page = new Page(title: 'Star Trek: The Motion Picture/Star Trek II: The Wrath of Khan')
		VideoTemplate videoTemplate = new VideoTemplate()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateContentsFixedValueEnricher.enrich(enrichablePair)

		then:
		1 * movieRepositoryMock.findByTitle('Star Trek: The Motion Picture') >> Optional.of(theMotionPicture)
		1 * movieRepositoryMock.findByTitle('Star Trek II: The Wrath of Khan') >> Optional.of(theWrathOfKhan)
		0 * _
		videoTemplate.series.empty
		videoTemplate.seasons.empty
		videoTemplate.movies == Set.of(theMotionPicture, theWrathOfKhan)
	}

}
