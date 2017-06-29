package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.season.WikitextToSeasonProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.series.processor.WikitextToSeriesProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class VideoTemplateContentsEnrichingProcessorTest extends Specification {

	private static final String FORMAT = 'FORMAT'
	private static final String SERIES = 'SERIES'
	private static final String SEASON = 'SEASON'
	private static final String DISCS_STRING = 'DISCS_STRING'
	private static final String DISCS_INTEGER = 3
	private static final VideoReleaseFormat VIDEO_RELEASE_FORMAT = VideoReleaseFormat.BLU_RAY

	private VideoReleaseFormatProcessor videoReleaseFormatProcessorMock

	private WikitextToSeriesProcessor wikitextToSeriesProcessorMock

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private WikitextToSeasonProcessor wikitextToSeasonProcessorMock

	private VideoTemplateContentsEnrichingProcessor videoTemplateContentsEnrichingProcessor

	void setup() {
		videoReleaseFormatProcessorMock = Mock()
		wikitextToSeriesProcessorMock = Mock()
		numberOfPartsProcessorMock = Mock()
		wikitextToSeasonProcessorMock = Mock()
		videoTemplateContentsEnrichingProcessor = new VideoTemplateContentsEnrichingProcessor(videoReleaseFormatProcessorMock,
				wikitextToSeriesProcessorMock, numberOfPartsProcessorMock, wikitextToSeasonProcessorMock)
	}

	void "when format part is found, VideoReleaseFormatProcessor is used to process it"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.FORMAT,
				value: FORMAT)))
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * videoReleaseFormatProcessorMock.process(FORMAT) >> VIDEO_RELEASE_FORMAT
		0 * _
		videoTemplate.format == VIDEO_RELEASE_FORMAT
	}

	void "when series part is found, WikitextToSeriesProcessor is used to process it"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.SERIES,
				value: SERIES)))
		Series series = Mock()
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * wikitextToSeriesProcessorMock.process(SERIES) >> series
		0 * _
		videoTemplate.series == series
	}

	void "when discs part is found, NumberOfPartsProcessor is used to process it"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.DISCS,
				value: DISCS_STRING)))
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * numberOfPartsProcessorMock.process(DISCS_STRING) >> DISCS_INTEGER
		0 * _
		videoTemplate.numberOfDataCarriers == DISCS_INTEGER
	}

	void "when season part is found, and WikitextToSeasonProcessor returns empty set, season is null"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.SEASON,
				value: SEASON)))
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * wikitextToSeasonProcessorMock.process(SEASON) >> Sets.newHashSet()
		0 * _
		videoTemplate.season == null
	}

	void "when season part is found, and WikitextToSeasonProcessor returns one result, it is used"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.SEASON,
				value: SEASON)))
		VideoTemplate videoTemplate = new VideoTemplate()
		Season season = Mock()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * wikitextToSeasonProcessorMock.process(SEASON) >> Sets.newHashSet(season)
		0 * _
		videoTemplate.season == season
	}

	void "when season part is found, and WikitextToSeasonProcessor returns more than one result, season is null"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.SEASON,
				value: SEASON)))
		VideoTemplate videoTemplate = new VideoTemplate()
		Season season1 = Mock()
		Season season2 = Mock()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * wikitextToSeasonProcessorMock.process(SEASON) >> Sets.newHashSet(season1, season2)
		0 * _
		videoTemplate.season == null
	}

}
