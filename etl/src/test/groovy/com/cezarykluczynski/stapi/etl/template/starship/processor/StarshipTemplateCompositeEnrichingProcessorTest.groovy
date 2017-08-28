package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class StarshipTemplateCompositeEnrichingProcessorTest extends Specification {

	private StarshipTemplateContentsEnrichingProcessor starshipTemplateContentsEnrichingProcessorMock

	private StarshipTemplateRelationsEnrichingProcessor starshipTemplateRelationsEnrichingProcessorMock

	private StarshipTemplateCompositeEnrichingProcessor starshipTemplateCompositeEnrichingProcessor

	void setup() {
		starshipTemplateContentsEnrichingProcessorMock = Mock()
		starshipTemplateRelationsEnrichingProcessorMock = Mock()
		starshipTemplateCompositeEnrichingProcessor = new StarshipTemplateCompositeEnrichingProcessor(
				starshipTemplateContentsEnrichingProcessorMock, starshipTemplateRelationsEnrichingProcessorMock)
	}

	void "passed enrichable pair to all dependencies"() {
		given:
		Template template = Mock()
		StarshipTemplate starshipTemplate = Mock()
		EnrichablePair<Template, StarshipTemplate> enrichablePair = EnrichablePair.of(template, starshipTemplate)

		when:
		starshipTemplateCompositeEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * starshipTemplateContentsEnrichingProcessorMock.enrich(enrichablePair)
		1 * starshipTemplateRelationsEnrichingProcessorMock.enrich(enrichablePair)
		0 * _
	}

}
