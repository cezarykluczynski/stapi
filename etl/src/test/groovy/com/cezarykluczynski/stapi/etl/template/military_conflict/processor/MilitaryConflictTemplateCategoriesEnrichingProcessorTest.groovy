package com.cezarykluczynski.stapi.etl.template.military_conflict.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class MilitaryConflictTemplateCategoriesEnrichingProcessorTest extends Specification {

	private MilitaryConflictTemplateCategoriesEnrichingProcessor militaryConflictTemplateCategoriesEnrichingProcessor

	void setup() {
		militaryConflictTemplateCategoriesEnrichingProcessor = new MilitaryConflictTemplateCategoriesEnrichingProcessor()
	}

	@SuppressWarnings('LineLength')
	@Unroll('set #flagName flag when #categoryTitleList is passed')
	void "sets flagName when categoryTitleList is passed"() {
		expect:
		militaryConflictTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryTitleList, militaryConflictTemplate))
		flag == militaryConflictTemplate[flagName]
		numberOfTrueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(militaryConflictTemplate)

		where:
		categoryTitleList                                             | militaryConflictTemplate                             | flagName           | flag  | numberOfTrueBooleans
		Lists.newArrayList()                                          | new MilitaryConflictTemplate()                       | 'earthConflict'    | false | 0
		Lists.newArrayList(CategoryTitle.EARTH_CONFLICTS)             | new MilitaryConflictTemplate()                       | 'earthConflict'    | true  | 1
		Lists.newArrayList()                                          | new MilitaryConflictTemplate(earthConflict: true)    | 'earthConflict'    | true  | 1
		Lists.newArrayList(CategoryTitle.EARTH_CONFLICTS_RETCONNED)   | new MilitaryConflictTemplate()                       | 'earthConflict'    | true  | 1
		Lists.newArrayList(CategoryTitle.CONFLICTS_ALTERNATE_REALITY) | new MilitaryConflictTemplate()                       | 'alternateReality' | true  | 1
		Lists.newArrayList()                                          | new MilitaryConflictTemplate(alternateReality: true) | 'alternateReality' | true  | 1
	}

}
