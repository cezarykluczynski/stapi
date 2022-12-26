package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

class VideoTemplateSeriesSeasonFromTitleEnrichingProcessorTest extends Specification {

	private static final String ABBREVIATION = 'TNG'
	private static final String TITLE = 'Star Trek: The Next Generation'
	private static final Long ID = 2

	private SeriesRepository seriesRepositoryMock

	private SeasonRepository seasonRepositoryMock

	private VideoTemplateSeriesSeasonFromTitleEnrichingProcessor videoTemplateSeriesSeasonFromTitleEnrichingProcessor

	void setup() {
		seriesRepositoryMock = Mock()
		seasonRepositoryMock = Mock()
		videoTemplateSeriesSeasonFromTitleEnrichingProcessor = new VideoTemplateSeriesSeasonFromTitleEnrichingProcessor(
				seriesRepositoryMock, seasonRepositoryMock)
	}

	void "gets series title when it matches exactly"() {
		given:
		Page page = new Page(title: 'Star Trek: The Next Generation (DVD)')
		VideoTemplate videoTemplate = new VideoTemplate()
		Series series = new Series(id: ID, abbreviation: ABBREVIATION, title: TITLE)
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateSeriesSeasonFromTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * seriesRepositoryMock.findAll() >> [series]
		1 * seriesRepositoryMock.findById(ID) >> Optional.of(series)
		0 * _
		videoTemplate.series == series
	}

	void "sets series when beginning matches"() {
		given:
		Page page = new Page(title: 'Star Trek: The Next Generation - 10th Anniversary Collector\'s Edition')
		VideoTemplate videoTemplate = new VideoTemplate()
		Series series = new Series(id: ID, abbreviation: ABBREVIATION, title: TITLE)
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateSeriesSeasonFromTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * seriesRepositoryMock.findAll() >> [series]
		1 * seriesRepositoryMock.findById(ID) >> Optional.of(series)
		0 * _
		videoTemplate.series == series
	}

	void "sets series when abbreviation matches"() {
		given:
		Page page = new Page(title: 'TNG Seasons 1-3 Collection')
		VideoTemplate videoTemplate = new VideoTemplate()
		Series series = new Series(id: ID, abbreviation: ABBREVIATION, title: TITLE)
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateSeriesSeasonFromTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * seriesRepositoryMock.findAll() >> [series]
		1 * seriesRepositoryMock.findById(ID) >> Optional.of(series)
		0 * _
		videoTemplate.series == series
	}

	void "when series is already set, sets season when it could be deducted from title"() {
		given:
		Page page = new Page(title: 'TNG Season 3 DVD')
		VideoTemplate videoTemplate = new VideoTemplate()
		Series series = new Series(id: ID, abbreviation: ABBREVIATION, title: TITLE)
		Season season = Mock()
		EnrichablePair<Page, VideoTemplate> enrichablePair = EnrichablePair.of(page, videoTemplate)

		when:
		videoTemplateSeriesSeasonFromTitleEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * seriesRepositoryMock.findAll() >> [series]
		1 * seriesRepositoryMock.findById(ID) >> Optional.of(series)
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(ABBREVIATION, 3) >> season
		0 * _
		videoTemplate.series == series
		videoTemplate.season == season
	}

}
