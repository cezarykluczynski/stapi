package com.cezarykluczynski.stapi.etl.template.military_conflict.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import spock.lang.Specification

class MilitaryConflictTemplatePartOfEnrichingProcessorTest extends Specification {

	private MilitaryConflictTemplatePartOfEnrichingProcessor militaryConflictTemplatePartOfEnrichingProcessor

	void setup() {
		militaryConflictTemplatePartOfEnrichingProcessor = new MilitaryConflictTemplatePartOfEnrichingProcessor(new WikitextApiImpl())
	}

	void "when part with 'Dominion War' link is found, dominionWar flag is set to true"() {
		given:
		Template.Part templatePart = new Template.Part(value: 'the [[Dominion War]]')
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()

		when:
		militaryConflictTemplatePartOfEnrichingProcessor.enrich(EnrichablePair.of(templatePart, militaryConflictTemplate))

		then:
		militaryConflictTemplate.dominionWarBattle
		ReflectionTestUtils.getNumberOfTrueBooleanFields(militaryConflictTemplate) == 1
	}

	void "when part with 'Klingon' or 'War' in link is found, klingonWar flag is set to true"() {
		given:
		Template.Part templatePart = new Template.Part(value: '<br>[[Federation-Klingon War (2372-73)]]<br>')
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()

		when:
		militaryConflictTemplatePartOfEnrichingProcessor.enrich(EnrichablePair.of(templatePart, militaryConflictTemplate))

		then:
		militaryConflictTemplate.klingonWar
		ReflectionTestUtils.getNumberOfTrueBooleanFields(militaryConflictTemplate) == 1
	}

	void "when part with 'War' and 'Klingon' in link is found, klingonWar flag is set to true"() {
		given:
		Template.Part templatePart = new Template.Part(value: 'the [[Klingon-Cardassian War]]')
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()

		when:
		militaryConflictTemplatePartOfEnrichingProcessor.enrich(EnrichablePair.of(templatePart, militaryConflictTemplate))

		then:
		militaryConflictTemplate.klingonWar
		ReflectionTestUtils.getNumberOfTrueBooleanFields(militaryConflictTemplate) == 1
	}

}
