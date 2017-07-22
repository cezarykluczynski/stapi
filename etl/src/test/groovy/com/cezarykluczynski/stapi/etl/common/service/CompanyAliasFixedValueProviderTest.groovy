package com.cezarykluczynski.stapi.etl.common.service

import spock.lang.Specification

class CompanyAliasFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Playmates'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private CompanyAliasFixedValueProvider companyAliasFixedValueProvider

	void setup() {
		companyAliasFixedValueProvider = new CompanyAliasFixedValueProvider()
	}

	void "provides correct range"() {
		expect:
		companyAliasFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		companyAliasFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 'Playmates Toys'
	}

	void "provides missing range"() {
		expect:
		!companyAliasFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		companyAliasFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
