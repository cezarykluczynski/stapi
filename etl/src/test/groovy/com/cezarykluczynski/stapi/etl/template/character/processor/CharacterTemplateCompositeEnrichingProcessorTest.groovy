package com.cezarykluczynski.stapi.etl.template.character.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.characterbox.processor.CharacterboxCharacterTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.fictional.processor.FictionalTemplateCompositeEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.hologram.processor.HologramTemplateCompositeEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplateCompositeEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualTemplatePlacesFixedValueProvider
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class CharacterTemplateCompositeEnrichingProcessorTest extends Specification {

	private TemplateFinder templateFinderMock

	private IndividualTemplateCompositeEnrichingProcessor individualTemplateCompositeEnrichingProcessorMock

	private CharacterTemplateFlagsEnrichingProcessor characterTemplateFlagsEnrichingProcessorMock

	private IndividualTemplatePlacesFixedValueProvider individualTemplatePlacesFixedValueProviderMock

	private CharacterboxCharacterTemplateEnrichingProcessor characterboxCharacterTemplateEnrichingProcessorMock

	private HologramTemplateCompositeEnrichingProcessor hologramTemplateCompositeEnrichingProcessorMock

	private FictionalTemplateCompositeEnrichingProcessor fictionalTemplateCompositeEnrichingProcessorMock

	private CharacterTemplateCompositeEnrichingProcessor characterTemplateCompositeEnrichingProcessor

	void setup() {
		templateFinderMock = Mock()
		individualTemplateCompositeEnrichingProcessorMock = Mock()
		characterTemplateFlagsEnrichingProcessorMock = Mock()
		individualTemplatePlacesFixedValueProviderMock = Mock()
		characterboxCharacterTemplateEnrichingProcessorMock  = Mock()
		hologramTemplateCompositeEnrichingProcessorMock = Mock()
		fictionalTemplateCompositeEnrichingProcessorMock = Mock()
		characterTemplateCompositeEnrichingProcessor = new CharacterTemplateCompositeEnrichingProcessor(templateFinderMock,
				individualTemplateCompositeEnrichingProcessorMock, characterTemplateFlagsEnrichingProcessorMock,
				individualTemplatePlacesFixedValueProviderMock, characterboxCharacterTemplateEnrichingProcessorMock,
				hologramTemplateCompositeEnrichingProcessorMock, fictionalTemplateCompositeEnrichingProcessorMock)
	}

	void "when fixed value and template are not found, minimal set of dependencies is called"() {
		Page page = new Page()
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * characterTemplateFlagsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Page, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == page
				assert enrichablePair.output == characterTemplate
		}
		1 * individualTemplatePlacesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.MBETA) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_HOLOGRAM) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_FICTIONAL) >> Optional.empty()
		0 * _
	}

	void "when fixed value and template are found, full set of dependencies is called"() {
		Page page = new Page()
		CharacterTemplate characterTemplate = new CharacterTemplate()
		Template sidebarIndividualTemplate = Mock()
		Template mbetaTemplate = Mock()
		Template sidebarHologramTemplate = Mock()
		Template sidebarFictionalTemplate = Mock()

		when:
		characterTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * characterTemplateFlagsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Page, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == page
				assert enrichablePair.output == characterTemplate
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.of(sidebarIndividualTemplate)
		1 * individualTemplatePlacesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * individualTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == sidebarIndividualTemplate
				assert enrichablePair.output == characterTemplate
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.MBETA) >> Optional.of(mbetaTemplate)
		1 * characterboxCharacterTemplateEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == mbetaTemplate
				assert enrichablePair.output == characterTemplate
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_HOLOGRAM) >> Optional.of(sidebarHologramTemplate)
		1 * hologramTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == sidebarHologramTemplate
				assert enrichablePair.output == characterTemplate
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_FICTIONAL) >> Optional.of(sidebarFictionalTemplate)
		1 * fictionalTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == sidebarFictionalTemplate
				assert enrichablePair.output == characterTemplate
		}
		0 * _
	}

}
