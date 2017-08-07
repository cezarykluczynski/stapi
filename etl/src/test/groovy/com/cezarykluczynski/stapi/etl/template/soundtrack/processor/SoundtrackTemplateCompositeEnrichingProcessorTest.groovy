package com.cezarykluczynski.stapi.etl.template.soundtrack.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class SoundtrackTemplateCompositeEnrichingProcessorTest extends Specification {

	private SoundtrackTemplateContentsEnrichingProcessor soundtrackTemplateContentsEnrichingProcessorMock

	private SoundtrackTemplateRelationsEnrichingProcessor soundtrackTemplateRelationsEnrichingProcessorMock

	private SoundtrackTemplateCompositeEnrichingProcessor soundtrackTemplateCompositeEnrichingProcessor

	void setup() {
		soundtrackTemplateContentsEnrichingProcessorMock = Mock()
		soundtrackTemplateRelationsEnrichingProcessorMock = Mock()
		soundtrackTemplateCompositeEnrichingProcessor = new SoundtrackTemplateCompositeEnrichingProcessor(
				soundtrackTemplateContentsEnrichingProcessorMock, soundtrackTemplateRelationsEnrichingProcessorMock)
	}

	void "passed enrichable pair to all dependencies"() {
		given:
		Template template = Mock()
		SoundtrackTemplate soundtrackTemplate = Mock()
		EnrichablePair<Template, SoundtrackTemplate> enrichablePair = EnrichablePair.of(template, soundtrackTemplate)

		when:
		soundtrackTemplateCompositeEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * soundtrackTemplateContentsEnrichingProcessorMock.enrich(enrichablePair)
		1 * soundtrackTemplateRelationsEnrichingProcessorMock.enrich(enrichablePair)
		0 * _
	}

}
