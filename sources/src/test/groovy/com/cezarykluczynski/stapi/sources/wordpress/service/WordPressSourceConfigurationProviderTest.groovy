package com.cezarykluczynski.stapi.sources.wordpress.service

import com.cezarykluczynski.stapi.sources.wordpress.api.enums.WordPressSource
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourceProperties
import com.cezarykluczynski.stapi.sources.wordpress.configuration.WordPressSourcesProperties
import spock.lang.Specification

class WordPressSourceConfigurationProviderTest extends Specification {

	private static final WordPressSource SOURCE = WordPressSource.STAR_TREK_CARDS

	private WordPressSourcesProperties wordPressSourcesPropertiesMock

	private WordPressSourceConfigurationProvider wordPressSourceConfigurationProvider

	void setup() {
		wordPressSourcesPropertiesMock = Mock()
		wordPressSourceConfigurationProvider = new WordPressSourceConfigurationProvider(wordPressSourcesPropertiesMock)
	}

	void "loads properties on first request, then doesn't load again on another request"() {
		given:
		WordPressSourceProperties wordPressSourceProperties = Mock()

		when:
		WordPressSourceProperties wordPressSourcePropertiesOutput = wordPressSourceConfigurationProvider.provideFor(SOURCE)

		then:
		1 * wordPressSourcesPropertiesMock.starTrekCards >> wordPressSourceProperties
		0 * _
		wordPressSourcePropertiesOutput == wordPressSourceProperties

		when:
		WordPressSourceProperties wordPressSourcePropertiesAnotherOutput = wordPressSourceConfigurationProvider.provideFor(SOURCE)

		then:
		0 * _
		wordPressSourcePropertiesAnotherOutput == wordPressSourceProperties
	}

}
