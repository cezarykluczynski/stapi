package com.cezarykluczynski.stapi.etl.organization.creation.processor

import spock.lang.Specification

class OrganizationNameFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Military Assault Command Operations (mirror)'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private OrganizationNameFixedValueProvider organizationNameFixedValueProvider

	void setup() {
		organizationNameFixedValueProvider = new OrganizationNameFixedValueProvider()
	}

	void "provides correct Organization name"() {
		expect:
		organizationNameFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		organizationNameFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 'Military Assault Command Operations'
	}

	void "provides missing Organization name"() {
		expect:
		!organizationNameFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		organizationNameFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
