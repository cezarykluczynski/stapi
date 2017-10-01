package com.cezarykluczynski.stapi.etl.template.spacecraft.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.common.processor.DateStatusProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.StatusProcessor
import com.cezarykluczynski.stapi.etl.template.spacecraft.dto.SpacecraftTemplateParameter
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class SpacecraftTemplateContentsEnrichingProcessorTest extends Specification {

	private static final String REGISTRY = 'REGISTRY'
	private static final String STATUS = 'STATUS'
	private static final String DATE_STATUS = 'DATE_STATUS'
	private static final String REGISTRY_RESULT = 'REGISTRY_RESULT'
	private static final String STATUS_RESULT = 'STATUS_RESULT'
	private static final String DATE_STATUS_RESULT = 'DATE_STATUS_RESULT'

	private SpacecraftRegistryProcessor spacecraftRegistryProcessorMock

	private StatusProcessor statusProcessorMock

	private DateStatusProcessor dateStatusProcessorMock

	private SpacecraftTemplateContentsEnrichingProcessor spacecraftTemplateContentsEnrichingProcessor

	void setup() {
		spacecraftRegistryProcessorMock = Mock()
		statusProcessorMock = Mock()
		dateStatusProcessorMock = Mock()
		spacecraftTemplateContentsEnrichingProcessor = new SpacecraftTemplateContentsEnrichingProcessor(spacecraftRegistryProcessorMock,
				statusProcessorMock, dateStatusProcessorMock)
	}

	void "when registry part is found, StarshipRegistryProcessor is used to process it"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.REGISTRY,
				value: REGISTRY)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * spacecraftRegistryProcessorMock.process(REGISTRY) >> REGISTRY_RESULT
		0 * _
		starshipTemplate.registry == REGISTRY_RESULT
	}

	void "when status part is found, StatusProcessor is used to process it"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.STATUS,
				value: STATUS)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * statusProcessorMock.process(STATUS) >> STATUS_RESULT
		0 * _
		starshipTemplate.status == STATUS_RESULT
	}

	void "when date status part is found, DateStatusProcessor is used to process it"() {
		given:
		Template sidebarStarshipTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: SpacecraftTemplateParameter.DATE_STATUS,
				value: DATE_STATUS)))
		StarshipTemplate starshipTemplate = new StarshipTemplate()

		when:
		spacecraftTemplateContentsEnrichingProcessor.enrich(EnrichablePair.of(sidebarStarshipTemplate, starshipTemplate))

		then:
		1 * dateStatusProcessorMock.process(DATE_STATUS) >> DATE_STATUS_RESULT
		0 * _
		starshipTemplate.dateStatus == DATE_STATUS_RESULT
	}

}
