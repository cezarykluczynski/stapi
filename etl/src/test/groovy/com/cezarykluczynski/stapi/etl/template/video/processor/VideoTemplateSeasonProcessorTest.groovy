package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.google.common.collect.Sets
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class VideoTemplateSeasonProcessorTest extends Specification {

	private static final String ABBREVIATION = 'DS9'
	private static final String WIKITEXT = 'WIKITEXT'

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private WikitextApi wikitextApiMock

	private SeasonRepository seasonRepositoryMock

	private VideoTemplateSeasonProcessor videoTemplateSeasonProcessor

	void setup() {
		wikitextToEntitiesProcessorMock = Mock()
		wikitextApiMock = Mock()
		seasonRepositoryMock = Mock()
		videoTemplateSeasonProcessor = new VideoTemplateSeasonProcessor(wikitextToEntitiesProcessorMock, wikitextApiMock, seasonRepositoryMock)
	}

	void "ignores empty value"() {
		given:
		VideoTemplate videoTemplate = new VideoTemplate()
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, ' ')

		expect:
		videoTemplateSeasonProcessor.process(item).empty
	}

	void "processes numeric season with existing series"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		Season season = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(series: Sets.newHashSet(series))
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, '3')

		when:
		Set<Season> foundSeasons = videoTemplateSeasonProcessor.process(item)

		then:
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(ABBREVIATION, 3) >> season
		0 * _
		foundSeasons == Set.of(season)
	}

	void "ignores numeric season when it cannot be found"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		VideoTemplate videoTemplate = new VideoTemplate(series: Sets.newHashSet(series))
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, '3')

		when:
		Set<Season> foundSeasons = videoTemplateSeasonProcessor.process(item)

		then:
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(ABBREVIATION, 3) >> null
		1 * wikitextToEntitiesProcessorMock.findSeasons('3') >> []
		0 * _
		foundSeasons.empty
	}

	void "gets seasons range"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		Season season1 = Mock()
		Season season2 = Mock()
		Season season3 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(series: Sets.newHashSet(series))
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, WIKITEXT)

		when:
		Set<Season> foundSeasons = videoTemplateSeasonProcessor.process(item)

		then:
		1 * wikitextApiMock.getWikitextWithoutLinks(WIKITEXT) >> '1 - 3'
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(ABBREVIATION, 1) >> season1
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(ABBREVIATION, 2) >> season2
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(ABBREVIATION, 3) >> season3
		0 * _
		foundSeasons == Set.of(season1, season2, season3)
	}

	void "gets seasons from wikitext"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		Season season = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(series: Sets.newHashSet(series))
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, WIKITEXT)

		when:
		Set<Season> foundSeasons = videoTemplateSeasonProcessor.process(item)

		then:
		1 * wikitextApiMock.getWikitextWithoutLinks(WIKITEXT) >> ''
		1 * wikitextToEntitiesProcessorMock.findSeasons(WIKITEXT) >> [season]
		0 * _
		foundSeasons == Set.of(season)
	}

	void "with more than one season from wikitext, all are added"() {
		given:
		Series series = new Series(abbreviation: ABBREVIATION)
		Season season1 = Mock()
		Season season2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(series: Sets.newHashSet(series))
		Pair<VideoTemplate, String> item = Pair.of(videoTemplate, WIKITEXT)

		when:
		Set<Season> foundSeasons = videoTemplateSeasonProcessor.process(item)

		then:
		1 * wikitextApiMock.getWikitextWithoutLinks(WIKITEXT) >> ''
		1 * wikitextToEntitiesProcessorMock.findSeasons(WIKITEXT) >> [season1, season2]
		0 * _
		foundSeasons == Set.of(season1, season2)
	}

}
