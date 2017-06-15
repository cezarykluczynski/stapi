package com.cezarykluczynski.stapi.etl.magazine.creation.processor

import spock.lang.Specification

class MagazineTemplatePublicationDatesFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Star Trek: The Official Fan Club Magazine issue 66'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private MagazineTemplatePublicationDatesFixedValueProvider magazineTemplatePublicationDatesFixedValueProvider

	void setup() {
		magazineTemplatePublicationDatesFixedValueProvider = new MagazineTemplatePublicationDatesFixedValueProvider()
	}

	void "provides correct publication dates"() {
		expect:
		magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.publicationDate.day == null
		magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.publicationDate.month == null
		magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.publicationDate.year == null
		magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.coverDate.day == null
		magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.coverDate.month == 2
		magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(EXISTING_TITLE).value.coverDate.year == 1989
	}

	void "provides missing publication dates"() {
		expect:
		!magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		magazineTemplatePublicationDatesFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
