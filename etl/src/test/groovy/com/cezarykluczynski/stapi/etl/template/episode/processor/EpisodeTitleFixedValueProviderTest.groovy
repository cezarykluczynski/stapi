package com.cezarykluczynski.stapi.etl.template.episode.processor

import spock.lang.Specification

class EpisodeTitleFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'E┬▓'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private EpisodeTitleFixedValueProvider episodeTitleFixedValueProvider

	void setup() {
		episodeTitleFixedValueProvider = new EpisodeTitleFixedValueProvider()
	}

	void "provides correct title"() {
		expect:
		episodeTitleFixedValueProvider.getSearchedValue(EXISTING_TITLE).found
		episodeTitleFixedValueProvider.getSearchedValue(EXISTING_TITLE).value == 'E²'
	}

	void "provides missing title"() {
		expect:
		!episodeTitleFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).found
		episodeTitleFixedValueProvider.getSearchedValue(NONEXISTING_TITLE).value == null
	}

}
