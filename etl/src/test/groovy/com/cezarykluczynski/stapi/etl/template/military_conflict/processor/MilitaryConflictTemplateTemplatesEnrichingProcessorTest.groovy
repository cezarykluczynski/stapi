package com.cezarykluczynski.stapi.etl.template.military_conflict.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class MilitaryConflictTemplateTemplatesEnrichingProcessorTest extends Specification {

	private MilitaryConflictTemplateTemplatesEnrichingProcessor militaryConflictTemplateTemplatesEnrichingProcessor

	void setup() {
		militaryConflictTemplateTemplatesEnrichingProcessor = new MilitaryConflictTemplateTemplatesEnrichingProcessor()
	}

	@SuppressWarnings('LineLength')
	@Unroll('set #flagName flag when #categoryTitleList is passed')
	void "sets flagName when categoryTitleList is passed"() {
		expect:
		militaryConflictTemplateTemplatesEnrichingProcessor.enrich(EnrichablePair.of(categoryTitleList, militaryConflictTemplate))
		flag == militaryConflictTemplate[flagName]
		numberOfTrueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(militaryConflictTemplate)

		where:
		categoryTitleList                                      | militaryConflictTemplate                              | flagName            | flag  | numberOfTrueBooleans
		Lists.newArrayList()                                   | new MilitaryConflictTemplate()                        | 'earthConflict'     | false | 0
		Lists.newArrayList(TemplateTitle.EARTHWARS)            | new MilitaryConflictTemplate()                        | 'earthConflict'     | true  | 1
		Lists.newArrayList()                                   | new MilitaryConflictTemplate(earthConflict: true)     | 'earthConflict'     | true  | 1
		Lists.newArrayList(TemplateTitle.FEDERATION_WARS)      | new MilitaryConflictTemplate()                        | 'federationWar'     | true  | 1
		Lists.newArrayList()                                   | new MilitaryConflictTemplate(federationWar: true)     | 'federationWar'     | true  | 1
		Lists.newArrayList(TemplateTitle.DOMINION_WAR_BATTLES) | new MilitaryConflictTemplate()                        | 'dominionWarBattle' | true  | 1
		Lists.newArrayList()                                   | new MilitaryConflictTemplate(dominionWarBattle: true) | 'dominionWarBattle' | true  | 1
		Lists.newArrayList(TemplateTitle.KLINGON_WARS)         | new MilitaryConflictTemplate()                        | 'klingonWar'        | true  | 1
		Lists.newArrayList()                                   | new MilitaryConflictTemplate(klingonWar: true)        | 'klingonWar'        | true  | 1
	}

}
