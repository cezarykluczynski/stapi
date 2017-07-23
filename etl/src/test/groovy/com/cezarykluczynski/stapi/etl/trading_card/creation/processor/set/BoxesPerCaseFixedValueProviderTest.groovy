package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set

import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit
import spock.lang.Specification

class BoxesPerCaseFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'The Making of Star Trek: The Next Generation'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private BoxesPerCaseFixedValueProvider boxesPerCaseFixedValueProvider

	void setup() {
		boxesPerCaseFixedValueProvider = new BoxesPerCaseFixedValueProvider()
	}

	void "provides ProductionRunDTO for existing title"() {
		expect:
		boxesPerCaseFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		boxesPerCaseFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.productionRun == 150000
		boxesPerCaseFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.productionRunUnit == ProductionRunUnit.SET
	}

	void "provides missing value for missing title"() {
		expect:
		!boxesPerCaseFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		boxesPerCaseFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
