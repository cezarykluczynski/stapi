package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.common.processor.DateStatusProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.StatusProcessor
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class StarshipTemplateContentsEnrichingProcessorTest extends Specification {

	private static final String REGISTRY = 'REGISTRY'
	private static final String STATUS = 'STATUS'
	private static final String DATE_STATUS = 'DATE_STATUS'
	private static final String REGISTRY_RESULT = 'REGISTRY_RESULT'
	private static final String STATUS_RESULT = 'STATUS_RESULT'
	private static final String DATE_STATUS_RESULT = 'DATE_STATUS_RESULT'

	private StarshipRegistryProcessor starshipRegistryProcessorMock

	private StatusProcessor statusProcessorMock

	private DateStatusProcessor dateStatusProcessorMock

	private StarshipTemplateContentsEnrichingProcessor starshipTemplateContentsEnrichingProcessor

	void setup() {
		starshipRegistryProcessorMock = Mock()
		statusProcessorMock = Mock()
		dateStatusProcessorMock = Mock()
		starshipTemplateContentsEnrichingProcessor = new StarshipTemplateContentsEnrichingProcessor(starshipRegistryProcessorMock,
				statusProcessorMock, dateStatusProcessorMock)
	}

	void "when registry part is found, StarshipRegistryProcessor is used to process it"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.REGISTRY,
				value: REGISTRY)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * starshipRegistryProcessorMock.process(REGISTRY) >> REGISTRY_RESULT
		0 * _
		starshipTemplate.registry == REGISTRY_RESULT
	}

	void "when status part is found, StatusProcessor is used to process it"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.STATUS,
				value: STATUS)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * statusProcessorMock.process(STATUS) >> STATUS_RESULT
		0 * _
		starshipTemplate.status == STATUS_RESULT
	}

	void "when date status part is found, DateStatusProcessor is used to process it"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: StarshipTemplateParameter.DATE_STATUS,
				value: DATE_STATUS)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		starshipTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * dateStatusProcessorMock.process(DATE_STATUS) >> DATE_STATUS_RESULT
		0 * _
		starshipTemplate.dateStatus == DATE_STATUS_RESULT
	}

}
