package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.model.series.entity.Series
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class VideoTemplateSeasonProcessorTest extends Specification {

	private static final String ABBREVIATION = 'DS9'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private SeasonRepository seasonRepositoryMock

	private VideoTemplateSeasonProcessor videoTemplateSeasonProcessor

	void setup() {
		wikitextToEntitiesProcessorMock = Mock()
		seasonRepositoryMock = Mock()
		videoTemplateSeasonProcessor = new VideoTemplateSeasonProcessor(wikitextToEntitiesProcessorMock, seasonRepositoryMock)
	}

	void "ignores empty value"() {
		given:
		VideoTemplate videoTemplate = new VideoTemplate()
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, ' ')

		expect:
		videoTemplateSeasonProcessor.process(item) == null
	}

	void "processes numeric season with existing series"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		Season season = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(series: series)
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, '3')

		when:
		Season foundSeason = videoTemplateSeasonProcessor.process(item)

		then:
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(ABBREVIATION, 3) >> season
		0 * _
		foundSeason == season
	}

	void "ignores numeric season when it cannot be found"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		VideoTemplate videoTemplate = new VideoTemplate(series: series)
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, '3')

		when:
		Season foundSeason = videoTemplateSeasonProcessor.process(item)

		then:
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(ABBREVIATION, 3) >> null
		1 * wikitextToEntitiesProcessorMock.findSeasons('3') >> []
		0 * _
		foundSeason == null
	}

	void "gets seasons from wikitext"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		Season season = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(series: series)
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, WIKITEXT)

		when:
		Season foundSeason = videoTemplateSeasonProcessor.process(item)

		then:
		1 * wikitextToEntitiesProcessorMock.findSeasons(WIKITEXT) >> [season]
		0 * _
		foundSeason == season
	}

	void "with more than one season from wikitext, none is used"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		Season season1 = Mock()
		Season season2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(series: series)
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, WIKITEXT)

		when:
		Season foundSeason = videoTemplateSeasonProcessor.process(item)

		then:
		1 * wikitextToEntitiesProcessorMock.findSeasons(WIKITEXT) >> [season1, season2]
		0 * _
		foundSeason == null
	}

}
