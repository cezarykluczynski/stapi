package com.cezarykluczynski.stapi.etl.company.creation.extractor

import com.cezarykluczynski.stapi.etl.company.creation.provider.CompanyNameFixedValueProvider
import spock.lang.Specification

class CompanyNameFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Space (channel)'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private CompanyNameFixedValueProvider companyNameFixedValueProvider

	void setup() {
		companyNameFixedValueProvider = new CompanyNameFixedValueProvider()
	}

	void "provides correct company name"() {
		expect:
		companyNameFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		companyNameFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 'Space'
	}

	void "provides missing company name"() {
		expect:
		!companyNameFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		companyNameFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
