package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class IndividualTemplateCompositeEnrichingProcessorTest extends Specification {

	private IndividualTemplateDateOfDeathEnrichingProcessor individualTemplateDateOfDeathEnrichingProcessorMock

	private IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessorMock

	private IndividualTemplateCompositeEnrichingProcessor individualTemplateCompositeEnrichingProcessor

	void setup() {
		individualTemplateDateOfDeathEnrichingProcessorMock = Mock()
		individualTemplatePartsEnrichingProcessorMock = Mock()
		individualTemplateCompositeEnrichingProcessor = new IndividualTemplateCompositeEnrichingProcessor(
				individualTemplateDateOfDeathEnrichingProcessorMock, individualTemplatePartsEnrichingProcessorMock)
	}

	void "passed enrichable pair to all dependencies"() {
		given:

		List<Template.Part> templatePartList = Mock()
		Template sidebarIndividualTemplate = new Template(parts: templatePartList)
		CharacterTemplate characterTemplate = Mock()
		EnrichablePair<Template, CharacterTemplate> enrichablePair = EnrichablePair.of(sidebarIndividualTemplate, characterTemplate)

		when:
		individualTemplateCompositeEnrichingProcessor.enrich(enrichablePair)

		then:
		1 * individualTemplateDateOfDeathEnrichingProcessorMock.enrich(enrichablePair)
		1 * individualTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<Template.Part>, CharacterTemplate> enrichablePairInput ->
			assert enrichablePairInput.input == templatePartList
			assert enrichablePairInput.output == characterTemplate
		}
		0 * _
	}

}
