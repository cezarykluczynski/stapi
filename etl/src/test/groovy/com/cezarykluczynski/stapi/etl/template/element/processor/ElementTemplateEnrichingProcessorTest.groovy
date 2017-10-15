package com.cezarykluczynski.stapi.etl.template.element.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.element.dto.ElementTemplateParameter
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class ElementTemplateEnrichingProcessorTest extends Specification {

	private static final String SYMBOL = 'SYMBOL'
	private static final String ATOMIC_NUMBER_VALID = '44'
	private static final String ATOMIC_NUMBER_INVALID = 'ATOMIC_NUMBER_INVALID'
	private static final Integer ATOMIC_NUMBER_INTEGER = 44
	private static final String ATOMIC_WEIGHT_VALID = '66'
	private static final String ATOMIC_WEIGHT_INVALID = 'ATOMIC_WEIGHT_INVALID'
	private static final Integer ATOMIC_WEIGHT_INTEGER = 66

	private ElementTemplateEnrichingProcessor elementTemplateEnrichingProcessor

	void setup() {
		elementTemplateEnrichingProcessor = new ElementTemplateEnrichingProcessor()
	}

	void "sets symbol"() {
		given:
		Template.Part templatePart = new Template.Part(key: ElementTemplateParameter.SYMBOL, value: SYMBOL)
		Element element = new Element()

		when:
		elementTemplateEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), element))

		then:
		0 * _
		element.symbol == SYMBOL
	}

	void "sets atomic number"() {
		given:
		Template.Part templatePart = new Template.Part(key: ElementTemplateParameter.A_NUM, value: ATOMIC_NUMBER_VALID)
		Element element = new Element()

		when:
		elementTemplateEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), element))

		then:
		0 * _
		element.atomicNumber == ATOMIC_NUMBER_INTEGER
	}

	void "tolerates invalid atomic number"() {
		given:
		Template.Part templatePart = new Template.Part(key: ElementTemplateParameter.A_NUM, value: ATOMIC_NUMBER_INVALID)
		Element element = new Element()

		when:
		elementTemplateEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), element))

		then:
		0 * _
		element.atomicNumber == null
	}

	void "sets atomic weight"() {
		given:
		Template.Part templatePart = new Template.Part(key: ElementTemplateParameter.A_WGT, value: ATOMIC_WEIGHT_VALID)
		Element element = new Element()

		when:
		elementTemplateEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), element))

		then:
		0 * _
		element.atomicWeight == ATOMIC_WEIGHT_INTEGER
	}

	void "tolerates invalid atomic weight"() {
		given:
		Template.Part templatePart = new Template.Part(key: ElementTemplateParameter.A_WGT, value: ATOMIC_WEIGHT_INVALID)
		Element element = new Element()

		when:
		elementTemplateEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), element))

		then:
		0 * _
		element.atomicWeight == null
	}

}
