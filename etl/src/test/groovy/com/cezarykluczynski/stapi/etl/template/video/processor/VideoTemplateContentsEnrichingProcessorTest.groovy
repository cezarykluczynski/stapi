package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.RunTimeProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.EpisodesCountDTO
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class VideoTemplateContentsEnrichingProcessorTest extends Specification {

	private static final String FORMAT = 'FORMAT'
	private static final String SERIES = 'SERIES'
	private static final String SEASON = 'SEASON'
	private static final String DISCS_STRING = 'DISCS_STRING'
	private static final String EPISODES = 'EPISODES'
	private static final String YEAR = 'YEAR'
	private static final String TIME_STRING = 'TIME_STRING'
	private static final Integer DISCS_INTEGER = 3
	private static final Integer NUMBER_OF_EPISODES = 14
	private static final Integer NUMBER_OF_FEATURE_LENGTH_EPISODES = 2
	private static final Integer YEAR_FROM = 2063
	private static final Integer YEAR_TO = 2373
	private static final Integer TIME_INTEGER = 907
	private static final VideoReleaseFormat VIDEO_RELEASE_FORMAT = VideoReleaseFormat.BLU_RAY

	private VideoReleaseFormatProcessor videoReleaseFormatProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private VideoReleaseNumberOfDataCarriersProcessor videoReleaseNumberOfDataCarriersProcessorMock

	private VideoTemplateEpisodesCountProcessor videoTemplateEpisodesCountProcessorMock

	private VideoTemplateYearsProcessor videoTemplateYearsProcessorMock

	private RunTimeProcessor runTimeProcessorMock

	private VideoTemplateSeasonProcessor videoTemplateSeasonProcessorMock

	private VideoTemplateContentsEnrichingProcessor videoTemplateContentsEnrichingProcessor

	void setup() {
		videoReleaseFormatProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		videoReleaseNumberOfDataCarriersProcessorMock = Mock()
		videoTemplateEpisodesCountProcessorMock = Mock()
		videoTemplateYearsProcessorMock = Mock()
		runTimeProcessorMock = Mock()
		videoTemplateSeasonProcessorMock = Mock()
		videoTemplateContentsEnrichingProcessor = new VideoTemplateContentsEnrichingProcessor(videoReleaseFormatProcessorMock,
				wikitextToEntitiesProcessorMock, videoReleaseNumberOfDataCarriersProcessorMock, videoTemplateEpisodesCountProcessorMock,
				videoTemplateYearsProcessorMock, runTimeProcessorMock, videoTemplateSeasonProcessorMock)
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

	void "when series part is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoTemplateParameter.SERIES,
				value: SERIES)
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Series series = Mock()
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findSeries(templatePart) >> Lists.newArrayList(series)
		0 * _
		videoTemplate.series == Set.of(series)
	}

	void "when series part is found, but series is already set, another one is added"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoTemplateParameter.SERIES,
				value: SERIES)
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Series series = Mock()
		Series series2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(series: Sets.newHashSet(series))

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findSeries(templatePart) >> Lists.newArrayList(series2)
		0 * _
		videoTemplate.series == Set.of(series, series2)
	}

	void "when WikitextToEntitiesProcessor returns empty list for series, series stays empty"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoTemplateParameter.SERIES,
				value: SERIES)
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(templatePart))
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findSeries(templatePart) >> Lists.newArrayList()
		0 * _
		videoTemplate.series.empty
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
		1 * videoReleaseNumberOfDataCarriersProcessorMock.process(DISCS_STRING) >> DISCS_INTEGER
		0 * _
		videoTemplate.numberOfDataCarriers == DISCS_INTEGER
	}

	void "when season part is found, value is set from VideoTemplateSeasonProcessor"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.SEASON,
				value: SEASON)))
		VideoTemplate videoTemplate = new VideoTemplate()
		Season season = Mock()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * videoTemplateSeasonProcessorMock.process(Pair.of(videoTemplate, SEASON)) >> Set.of(season)
		0 * _
		videoTemplate.seasons == Set.of(season)
	}

	void "when season part is found, but season is already set, other season is added"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.SEASON,
				value: SEASON)))
		Season season = Mock()
		Season season2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate(seasons: Sets.newHashSet(season))

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * videoTemplateSeasonProcessorMock.process(Pair.of(videoTemplate, SEASON)) >> Set.of(season2)
		0 * _
		videoTemplate.seasons == Set.of(season, season2)
	}

	void "when episodes part is found, VideoTemplateEpisodesCountProcessor is used to process it"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.EPISODES,
				value: EPISODES)))
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * videoTemplateEpisodesCountProcessorMock.process(EPISODES) >> new EpisodesCountDTO(
				numberOfEpisodes: NUMBER_OF_EPISODES,
				numberOfFeatureLengthEpisodes: NUMBER_OF_FEATURE_LENGTH_EPISODES)
		0 * _
		videoTemplate.numberOfEpisodes == NUMBER_OF_EPISODES
		videoTemplate.numberOfFeatureLengthEpisodes == NUMBER_OF_FEATURE_LENGTH_EPISODES
	}

	void "when year part is found, VideoTemplateYearsProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoTemplateParameter.YEAR,
				value: YEAR)
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(templatePart))
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * videoTemplateYearsProcessorMock.process(templatePart) >> new YearRange(
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO)
		0 * _
		videoTemplate.yearFrom == YEAR_FROM
		videoTemplate.yearTo == YEAR_TO
	}

	void "when year part is found, and VideoTemplateYearsProcessor returns null, nothing happens"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoTemplateParameter.YEAR,
				value: YEAR)
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(templatePart))
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * videoTemplateYearsProcessorMock.process(templatePart) >> null
		0 * _
		videoTemplate.yearFrom == null
		videoTemplate.yearTo == null
	}

	void "when time part is found, RunTimeProcessor is used to process it"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.TIME,
				value: TIME_STRING)))
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * runTimeProcessorMock.process(TIME_STRING) >> TIME_INTEGER
		0 * _
		videoTemplate.runTime == TIME_INTEGER
	}

}
