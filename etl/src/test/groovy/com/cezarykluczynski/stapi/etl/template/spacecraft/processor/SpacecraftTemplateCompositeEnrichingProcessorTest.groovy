package com.cezarykluczynski.stapi.etl.template.spacecraft.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class SpacecraftTemplateCompositeEnrichingProcessorTest extends Specification {

	private SpacecraftTemplateContentsEnrichingProcessor spacecraftTemplateContentsEnrichingProcessorMock

	private SpacecraftTemplateRelationsEnrichingProcessor spacecraftTemplateRelationsEnrichingProcessorMock

	private SpacecraftTemplateCompositeEnrichingProcessor spacecraftTemplateCompositeEnrichingProcessor

	void setup() {
		spacecraftTemplateContentsEnrichingProcessorMock = Mock()
		spacecraftTemplateRelationsEnrichingProcessorMock = Mock()
		spacecraftTemplateCompositeEnrichingProcessor = new SpacecraftTemplateCompositeEnrichingProcessor(
				spacecraftTemplateContentsEnrichingProcessorMock, spacecraftTemplateRelationsEnrichingProcessorMock)
	}

	void "passed enrichable pair to all dependencies"() {
		given:
		Template template = Mock()
		StarshipTemplate starshipTemplate = Mock()
		EnrichablePair<Template, StarshipTemplate> enrichablePair = EnrichablePair.of(template, starshipTemplate)

		when:
		spacecraftTemplateCompositeEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * spacecraftTemplateContentsEnrichingProcessorMock.enrich(enrichablePair)
		1 * spacecraftTemplateRelationsEnrichingProcessorMock.enrich(enrichablePair)
		0 * _
	}

}
