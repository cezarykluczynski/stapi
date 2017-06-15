package com.cezarykluczynski.stapi.etl.magazine.creation.processor

import spock.lang.Specification

class MagazineTemplateNumberOfPagesFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Star Trek 30 Years'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private MagazineTemplateNumberOfPagesFixedValueProvider magazineTemplateNumberOfPagesFixedValueProvider

	void setup() {
		magazineTemplateNumberOfPagesFixedValueProvider = new MagazineTemplateNumberOfPagesFixedValueProvider()
	}

	void "provides correct number of pages"() {
		expect:
		magazineTemplateNumberOfPagesFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		magazineTemplateNumberOfPagesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 168
	}

	void "provides missing number of pages"() {
		expect:
		!magazineTemplateNumberOfPagesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		magazineTemplateNumberOfPagesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
