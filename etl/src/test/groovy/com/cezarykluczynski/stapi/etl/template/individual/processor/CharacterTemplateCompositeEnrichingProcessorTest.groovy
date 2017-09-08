package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.characterbox.processor.CharacterboxCharacterTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.fictional.processor.FictionalTemplateCompositeEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.hologram.processor.HologramTemplateCompositeEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.CharacterTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class CharacterTemplateCompositeEnrichingProcessorTest extends Specification {

	private TemplateFinder templateFinderMock

	private IndividualTemplateDateOfDeathEnrichingProcessor individualDateOfDeathEnrichingProcessorMock

	private IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessorMock

	private CharacterTemplateMirrorAlternateUniverseEnrichingProcessor characterTemplateMirrorAlternateUniverseEnrichingProcessorMock

	private IndividualTemplatePlacesFixedValueProvider individualTemplatePlacesFixedValueProviderMock

	private CharacterboxCharacterTemplateEnrichingProcessor characterboxCharacterTemplateEnrichingProcessorMock

	private HologramTemplateCompositeEnrichingProcessor hologramTemplateCompositeEnrichingProcessorMock

	private FictionalTemplateCompositeEnrichingProcessor fictionalTemplateCompositeEnrichingProcessorMock

	private CharacterTemplateCompositeEnrichingProcessor characterTemplateCompositeEnrichingProcessor

	void setup() {
		templateFinderMock = Mock()
		individualDateOfDeathEnrichingProcessorMock = Mock()
		individualTemplatePartsEnrichingProcessorMock = Mock()
		characterTemplateMirrorAlternateUniverseEnrichingProcessorMock = Mock()
		individualTemplatePlacesFixedValueProviderMock = Mock()
		characterboxCharacterTemplateEnrichingProcessorMock  = Mock()
		hologramTemplateCompositeEnrichingProcessorMock = Mock()
		fictionalTemplateCompositeEnrichingProcessorMock = Mock()
		characterTemplateCompositeEnrichingProcessor = new CharacterTemplateCompositeEnrichingProcessor(templateFinderMock,
				individualDateOfDeathEnrichingProcessorMock, individualTemplatePartsEnrichingProcessorMock,
				characterTemplateMirrorAlternateUniverseEnrichingProcessorMock, individualTemplatePlacesFixedValueProviderMock,
				characterboxCharacterTemplateEnrichingProcessorMock, hologramTemplateCompositeEnrichingProcessorMock,
				fictionalTemplateCompositeEnrichingProcessorMock)
	}

	void "when fixed value and template are not found, minimal set of dependencies is called"() {
		Page page = new Page()
		CharacterTemplate characterTemplate = new CharacterTemplate()

		when:
		characterTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
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
		Template.Part templatePart = Mock()
		List<Template.Part> templatePartList = Lists.newArrayList(templatePart)
		Template sidebarIndividualTemplate = new Template(parts: templatePartList)
		Template mbetaTemplate = Mock()
		Template sidebarHologramTemplate = Mock()
		Template sidebarFictionalTemplate = Mock()

		when:
		characterTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, characterTemplate))

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_INDIVIDUAL) >> Optional.of(sidebarIndividualTemplate)
		1 * individualTemplatePlacesFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * individualDateOfDeathEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == sidebarIndividualTemplate
				assert enrichablePair.output == characterTemplate
		}
		1 * individualTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<Template.Part>, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == templatePartList
				assert enrichablePair.output == characterTemplate
		}
		1 * characterTemplateMirrorAlternateUniverseEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Page, CharacterTemplate> enrichablePair ->
				assert enrichablePair.input == page
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
