package com.cezarykluczynski.stapi.etl.element.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class ElementTemplateTitlesEnrichingProcessorTest extends Specification {

	ElementTemplateTitlesEnrichingProcessor elementTemplateTitlesEnrichingProcessor

	void setup() {
		elementTemplateTitlesEnrichingProcessor = new ElementTemplateTitlesEnrichingProcessor()
	}

	@Unroll('set #flagName flag when #page is passed; expect #trueBooleans not null fields')
	void "sets flagName when page is passed"() {
		given:
		Element element = new Element()

		expect:
		elementTemplateTitlesEnrichingProcessor.enrich(EnrichablePair.of(templateTitleList, element))
		element[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(element) == trueBooleans

		where:
		templateTitleList                                  | flagName           | flag  | trueBooleans
		Lists.newArrayList()                               | 'gammaSeries'      | false | 0
		Lists.newArrayList(TemplateTitle.GAMMASERIES)      | 'gammaSeries'      | true  | 1
		Lists.newArrayList(TemplateTitle.HYPERSONICSERIES) | 'hypersonicSeries' | true  | 1
		Lists.newArrayList(TemplateTitle.MEGASERIES)       | 'megaSeries'       | true  | 1
		Lists.newArrayList(TemplateTitle.OMEGASERIES)      | 'omegaSeries'      | true  | 1
		Lists.newArrayList(TemplateTitle.TRANSONICSERIES)  | 'transonicSeries'  | true  | 1
		Lists.newArrayList(TemplateTitle.WORLD_SERIES)     | 'worldSeries'      | true  | 1
	}

}
