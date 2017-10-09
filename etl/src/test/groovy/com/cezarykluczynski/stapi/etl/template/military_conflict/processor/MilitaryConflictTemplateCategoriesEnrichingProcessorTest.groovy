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

	@Unroll('set #flagName flag when #categoryTitleList is passed')
	void "sets flagName when categoryTitleList is passed"() {
		given:
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()

		expect:
		militaryConflictTemplateCategoriesEnrichingProcessor.enrich(EnrichablePair.of(categoryTitleList, militaryConflictTemplate))
		flag == militaryConflictTemplate[flagName]
		numberOfTrueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(militaryConflictTemplate)

		where:
		categoryTitleList                                             | flagName           | flag  | numberOfTrueBooleans
		Lists.newArrayList()                                          | 'earthConflict'    | false | 0
		Lists.newArrayList(CategoryTitle.EARTH_CONFLICTS)             | 'earthConflict'    | true  | 1
		Lists.newArrayList(CategoryTitle.EARTH_CONFLICTS_RETCONNED)   | 'earthConflict'    | true  | 1
		Lists.newArrayList(CategoryTitle.CONFLICTS_ALTERNATE_REALITY) | 'alternateReality' | true  | 1
	}

}
