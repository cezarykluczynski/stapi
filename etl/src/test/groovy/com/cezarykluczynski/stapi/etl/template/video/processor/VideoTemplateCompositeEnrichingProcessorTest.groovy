package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class VideoTemplateCompositeEnrichingProcessorTest extends Specification {

	private VideoTemplateContentsEnrichingProcessor videoTemplateContentsEnrichingProcessorMock

	private VideoTemplateDatesEnrichingProcessor videoTemplateDatesEnrichingProcessorMock

	private VideoTemplateDigitalFormatsEnrichingProcessor videoTemplateDigitalFormatsEnrichingProcessorMock

	private VideoTemplateRelationsEnrichingProcessor videoTemplateRelationsEnrichingProcessorMock

	private VideoTemplateCompositeEnrichingProcessor videoTemplateCompositeEnrichingProcessor

	void setup() {
		videoTemplateContentsEnrichingProcessorMock = Mock()
		videoTemplateDatesEnrichingProcessorMock = Mock()
		videoTemplateDigitalFormatsEnrichingProcessorMock = Mock()
		videoTemplateRelationsEnrichingProcessorMock = Mock()
		videoTemplateCompositeEnrichingProcessor = new VideoTemplateCompositeEnrichingProcessor(videoTemplateContentsEnrichingProcessorMock,
				videoTemplateDatesEnrichingProcessorMock, videoTemplateDigitalFormatsEnrichingProcessorMock,
				videoTemplateRelationsEnrichingProcessorMock)
	}

	void "passed enrichable pair to all dependencies"() {
		given:
		Template template = Mock()
		VideoTemplate videoTemplate = Mock()
		EnrichablePair<Template, VideoTemplate> enrichablePair = EnrichablePair.of(template, videoTemplate)

		when:
		videoTemplateCompositeEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * videoTemplateContentsEnrichingProcessorMock.enrich(enrichablePair)
		1 * videoTemplateDatesEnrichingProcessorMock.enrich(enrichablePair)
		1 * videoTemplateDigitalFormatsEnrichingProcessorMock.enrich(enrichablePair)
		1 * videoTemplateRelationsEnrichingProcessorMock.enrich(enrichablePair)
		0 * _
	}

}
