package com.cezarykluczynski.stapi.etl.company.creation.extractor

import com.cezarykluczynski.stapi.etl.company.creation.provider.CompanyNameFixedValueProvider
import spock.lang.Specification

class CompanyNameFixedValueProviderTest extends Specification {

	private CompanyNameFixedValueProvider companyNameFixedValueProvider

	void setup() {
		companyNameFixedValueProvider = new CompanyNameFixedValueProvider()
	}

	void "provides correct company name"() {
		expect:
		companyNameFixedValueProvider.getSearchedValue('RT├ë').found
		companyNameFixedValueProvider.getSearchedValue('RT├ë').value == 'RTÉ'
	}

	void "provides missing company name"() {
		expect:
		!companyNameFixedValueProvider.getSearchedValue('Not found').found
	}

}
