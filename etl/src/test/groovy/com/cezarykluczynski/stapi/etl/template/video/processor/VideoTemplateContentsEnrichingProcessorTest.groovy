package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.series.processor.WikitextToSeriesProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.video_release.entity.enums.VideoReleaseFormat
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class VideoTemplateContentsEnrichingProcessorTest extends Specification {

	private static final String FORMAT = 'FORMAT'
	private static final String SERIES = 'SERIES'
	private static final VideoReleaseFormat VIDEO_RELEASE_FORMAT = VideoReleaseFormat.BLU_RAY

	private VideoReleaseFormatProcessor videoReleaseFormatProcessorMock

	private WikitextToSeriesProcessor wikitextToSeriesProcessorMock

	private VideoTemplateContentsEnrichingProcessor videoTemplateContentsEnrichingProcessor

	void setup() {
		videoReleaseFormatProcessorMock = Mock()
		wikitextToSeriesProcessorMock = Mock()
		videoTemplateContentsEnrichingProcessor = new VideoTemplateContentsEnrichingProcessor(videoReleaseFormatProcessorMock,
				wikitextToSeriesProcessorMock)
	}

	void "when format part is found, VideoReleaseFormatProcessor is used to set it"() {
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

	void "when series part is found, WikitextToSeriesProcessor is used to set it"() {
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

}
