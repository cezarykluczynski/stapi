package com.cezarykluczynski.stapi.etl.template.video_game.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class VideoGameTemplateCompositeEnrichingProcessorTest extends Specification {

	private VideoGameTemplateContentsEnrichingProcessor videoGameTemplateContentsEnrichingProcessorMock

	private VideoGameTemplateRelationsEnrichingProcessor videoGameTemplateRelationsEnrichingProcessorMock

	private VideoGameTemplateCompositeEnrichingProcessor videoGameTemplateCompositeEnrichingProcessor

	void setup() {
		videoGameTemplateContentsEnrichingProcessorMock = Mock()
		videoGameTemplateRelationsEnrichingProcessorMock = Mock()
		videoGameTemplateCompositeEnrichingProcessor = new VideoGameTemplateCompositeEnrichingProcessor(
				videoGameTemplateContentsEnrichingProcessorMock, videoGameTemplateRelationsEnrichingProcessorMock)
	}

	void "passed enrichable pair to all dependencies"() {
		given:
		Template template = Mock()
		VideoGameTemplate videoGameTemplate = Mock()
		EnrichablePair<Template, VideoGameTemplate> enrichablePair = EnrichablePair.of(template, videoGameTemplate)

		when:
		videoGameTemplateCompositeEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * videoGameTemplateContentsEnrichingProcessorMock.enrich(enrichablePair)
		1 * videoGameTemplateRelationsEnrichingProcessorMock.enrich(enrichablePair)
		0 * _
	}

}
