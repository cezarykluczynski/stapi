package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryPlacesDTO
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class IndividualTemplateCompositeEnrichingProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	private static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'

	private TemplateFinder templateFinderMock

	private IndividualTemplateDateOfDeathEnrichingProcessor individualDateOfDeathEnrichingProcessorMock

	private IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessorMock

	private IndividualMirrorAlternateUniverseEnrichingProcessor individualMirrorAlternateUniverseEnrichingProcessorMock

	private IndividualTemplateCompositeEnrichingProcessor individualTemplateCompositeEnrichingProcessor

	private IndividualTemplatePlacesFixedValueProvider individualTemplatePlacesFixedValueProviderMock

	void setup() {
		templateFinderMock = Mock(TemplateFinder)
		individualDateOfDeathEnrichingProcessorMock = Mock(IndividualTemplateDateOfDeathEnrichingProcessor)
		individualTemplatePartsEnrichingProcessorMock = Mock(IndividualTemplatePartsEnrichingProcessor)
		individualMirrorAlternateUniverseEnrichingProcessorMock = Mock(IndividualMirrorAlternateUniverseEnrichingProcessor)
		individualTemplatePlacesFixedValueProviderMock = Mock(IndividualTemplatePlacesFixedValueProvider)
		individualTemplateCompositeEnrichingProcessor = new IndividualTemplateCompositeEnrichingProcessor(templateFinderMock,
				individualDateOfDeathEnrichingProcessorMock, individualTemplatePartsEnrichingProcessorMock,
				individualMirrorAlternateUniverseEnrichingProcessorMock, individualTemplatePlacesFixedValueProviderMock)
	}

	void "when sidebar individual template is not found, enrichers are not called"() {
		given:
		Page page = new Page()
		IndividualTemplate individualTemplate = new IndividualTemplate()

		when:
		individualTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * individualTemplatePlacesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		0 * _
	}

	void "when IndividualLifeBoundaryPlacesDTO is found for page title, it is used to enrich individual template"() {
		given:
		Page page = new Page(title: TITLE)
		IndividualTemplate individualTemplate = new IndividualTemplate()
		IndividualLifeBoundaryPlacesDTO individualLifeBoundaryPlacesDTO = IndividualLifeBoundaryPlacesDTO.of(PLACE_OF_BIRTH, PLACE_OF_DEATH)

		when:
		individualTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * individualTemplatePlacesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(individualLifeBoundaryPlacesDTO)
		0 * _
		individualTemplate.placeOfBirth == PLACE_OF_BIRTH
		individualTemplate.placeOfDeath == PLACE_OF_DEATH
	}

	void "when sidebar individual template is found, enrichers not called"() {
		given:
		Page page = new Page()
		IndividualTemplate individualTemplate = new IndividualTemplate()
		List<Template.Part> templatePartList = Lists.newArrayList(Mock(Template.Part))
		Template template = new Template(parts: templatePartList)

		when:
		individualTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, individualTemplate))

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_INDIVIDUAL) >> Optional.of(template)
		1 * individualTemplatePlacesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template, IndividualTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output == individualTemplate
		}
		1 * individualTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<Template.Part>, IndividualTemplate> enrichablePair ->
			assert enrichablePair.input == templatePartList
			assert enrichablePair.output == individualTemplate
		}
		1 * individualMirrorAlternateUniverseEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Page, IndividualTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output == individualTemplate
		}
		0 * _
	}

}
