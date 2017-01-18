package com.cezarykluczynski.stapi.etl.template.episode.processor

import spock.lang.Specification

class EpisodeTitleFixedValueProviderTest extends Specification {

	private EpisodeTitleFixedValueProvider episodeTitleFixedValueProvider

	def setup() {
		episodeTitleFixedValueProvider = new EpisodeTitleFixedValueProvider()
	}

	def "provides correct title"() {
		expect:
		episodeTitleFixedValueProvider.getSearchedValue('E┬▓').found
		episodeTitleFixedValueProvider.getSearchedValue('E┬▓').value == 'E²'
	}

}
